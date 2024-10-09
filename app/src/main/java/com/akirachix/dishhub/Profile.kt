package com.akirachix.dishhub

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Profile : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var reminderSwitch: Switch
    private lateinit var pantryUpdateRadioGroup: RadioGroup
    private lateinit var saveProfileButton: Button
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var txtOn: TextView
    private lateinit var txtOff: TextView
    private lateinit var monthlyRadioButton: RadioButton
    private lateinit var weeklyRadioButton: RadioButton

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY = 2
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImageView = findViewById(R.id.profileImageView)
        nameTextView = findViewById(R.id.nameTextView)
        nameEditText = findViewById(R.id.nameEditText)
//        usernameEditText = findViewById(R.id.usernameEditText)
        pantryUpdateRadioGroup = findViewById(R.id.pantryUpdateRadioGroup)
        reminderSwitch = findViewById(R.id.reminderSwitch)
        saveProfileButton = findViewById(R.id.saveProfileButton)
        txtOn = findViewById(R.id.txton)
        txtOff = findViewById(R.id.txtoff)
        monthlyRadioButton = findViewById(R.id.monthlyRadioButton)
        weeklyRadioButton = findViewById(R.id.weeklyRadioButton)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        createNotificationChannel()

        reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                scheduleReminder()
            } else {
                cancelReminder()
            }
        }

        // Click listener for the profile image
        profileImageView.setOnClickListener {
            showImagePickerDialog()
        }

        saveProfileButton.setOnClickListener {
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show()
        }

        // Back button logic
        val backButton: ImageView = findViewById(R.id.btnback)
        backButton.setOnClickListener {
            finish() // Ends this activity and returns to the previous one
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Choose Profile Photo")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    profileImageView.setImageBitmap(bitmap) // Display the photo taken
                }
                REQUEST_GALLERY -> {
                    imageUri = data?.data
                    profileImageView.setImageURI(imageUri) // Display the selected photo from gallery
                }
            }
        }
    }

    private fun scheduleReminder() {
        cancelReminder()
        val selectedId = pantryUpdateRadioGroup.checkedRadioButtonId
        val frequency = if (selectedId == R.id.monthlyRadioButton) {
            AlarmManager.INTERVAL_DAY * 30
        } else {
            AlarmManager.INTERVAL_DAY * 7
        }
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + frequency, frequency, pendingIntent)
        Toast.makeText(this, "Reminder set!", Toast.LENGTH_SHORT).show()
    }

    private fun cancelReminder() {
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Reminder canceled!", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pantry Reminder"
            val descriptionText = "Channel for pantry reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("reminderChannel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}





//
//package com.akirachix.dishhub
//
//import android.Manifest
//import android.app.AlarmManager
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.SystemClock
//import android.provider.MediaStore
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.RadioButton
//import android.widget.RadioGroup
//import android.widget.Switch
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.akirachix.dishhub.R.id.usernameEditText
//
//class Profile : AppCompatActivity() {
//
//    private lateinit var alarmManager: AlarmManager
//    private lateinit var reminderSwitch: Switch
//    private lateinit var pantryUpdateRadioGroup: RadioGroup
//    private lateinit var saveProfileButton: Button
//    private lateinit var logoutButton: Button
//    private lateinit var profileImageView: ImageView
//    private lateinit var nameTextView: TextView
//    private lateinit var nameEditText: EditText
//    private lateinit var usernameEditText: EditText
//    private lateinit var txtOn: TextView
//    private lateinit var txtOff: TextView
//    private lateinit var monthlyRadioButton: RadioButton
//    private lateinit var weeklyRadioButton: RadioButton
//
//    private val REQUEST_IMAGE_CAPTURE = 1
//    private val REQUEST_GALLERY = 2
//    private var imageUri: Uri? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//
//        // Bind views
//        profileImageView = findViewById(R.id.profileImageView)
//        nameTextView = findViewById(R.id.nameTextView)
//        nameEditText = findViewById(R.id.nameEditText)
//        pantryUpdateRadioGroup = findViewById(R.id.pantryUpdateRadioGroup)
//        reminderSwitch = findViewById(R.id.reminderSwitch)
//        saveProfileButton = findViewById(R.id.saveProfileButton)
//        txtOn = findViewById(R.id.txton)
//        txtOff = findViewById(R.id.txtoff)
//        monthlyRadioButton = findViewById(R.id.monthlyRadioButton)
//        weeklyRadioButton = findViewById(R.id.weeklyRadioButton)
//
//        // Initialize AlarmManager and Notification Channel
//        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        createNotificationChannel()
//
//        // Handle reminder switch changes
//        reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                scheduleReminder()
//            } else {
//                cancelReminder()
//            }
//        }
//
//        // Click listener for the profile image
//        profileImageView.setOnClickListener {
//            showImagePickerDialog()
//        }
//
//        // Save profile button click listener
//        saveProfileButton.setOnClickListener {
//            val username = usernameEditText.text.toString()
//            val name = nameEditText.text.toString()
//            // Add logic to save username and name
//            // For now, we'll just show a Toast
//            Toast.makeText(this, "Profile Saved! Name: $name, Username: $username", Toast.LENGTH_SHORT).show()
//        }
//
//        // Logout button click listener
//        logoutButton.setOnClickListener {
//            // Handle logout logic here
//            // For example, clear user session or navigate to login screen
//            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
//            finish() // End this activity
//        }
//
//        // Back button logic
//        val backButton: ImageView = findViewById(R.id.btnback)
//        backButton.setOnClickListener {
//            finish() // Ends this activity and returns to the previous one
//        }
//    }
//
//    private fun showImagePickerDialog() {
//        val options = arrayOf("Take Photo", "Choose from Gallery")
//        val builder = android.app.AlertDialog.Builder(this)
//        builder.setTitle("Choose Profile Photo")
//        builder.setItems(options) { _, which ->
//            when (which) {
//                0 -> openCamera()
//                1 -> openGallery()
//            }
//        }
//        builder.show()
//    }
//
//    private fun openCamera() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (takePictureIntent.resolveActivity(packageManager) != null) {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
//        }
//    }
//
//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, REQUEST_GALLERY)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            when (requestCode) {
//                REQUEST_IMAGE_CAPTURE -> {
//                    val bitmap = data?.extras?.get("data") as Bitmap
//                    profileImageView.setImageBitmap(bitmap) // Display the photo taken
//                }
//                REQUEST_GALLERY -> {
//                    imageUri = data?.data
//                    profileImageView.setImageURI(imageUri) // Display the selected photo from gallery
//                }
//            }
//        }
//    }
//
//    private fun scheduleReminder() {
//        cancelReminder()
//        val selectedId = pantryUpdateRadioGroup.checkedRadioButtonId
//        val frequency = if (selectedId == R.id.monthlyRadioButton) {
//            AlarmManager.INTERVAL_DAY * 30
//        } else {
//            AlarmManager.INTERVAL_DAY * 7
//        }
//        val intent = Intent(this, ReminderReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        alarmManager.setInexactRepeating(
//            AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime() + frequency, frequency, pendingIntent
//        )
//        Toast.makeText(this, "Reminder set!", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun cancelReminder() {
//        val intent = Intent(this, ReminderReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        alarmManager.cancel(pendingIntent)
//        Toast.makeText(this, "Reminder canceled!", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Pantry Reminder"
//            val descriptionText = "Channel for pantry reminders"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel("reminderChannel", name, importance).apply {
//                description = descriptionText
//            }
//            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//}