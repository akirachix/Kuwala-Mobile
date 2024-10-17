





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
import android.provider.MediaStore
import android.widget.*
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

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView)
        nameTextView = findViewById(R.id.nameTextView)
        nameEditText = findViewById(R.id.nameEditText)
        pantryUpdateRadioGroup = findViewById(R.id.pantryUpdateRadioGroup)
        reminderSwitch = findViewById(R.id.reminderSwitch)
        saveProfileButton = findViewById(R.id.saveProfileButton)
        txtOn = findViewById(R.id.txton)
        txtOff = findViewById(R.id.txtoff)
        monthlyRadioButton = findViewById(R.id.monthlyRadioButton)
        weeklyRadioButton = findViewById(R.id.weeklyRadioButton)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        createNotificationChannel()

        // Timer reminder switch
        reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            pantryUpdateRadioGroup.visibility = if (isChecked) RadioGroup.VISIBLE else RadioGroup.GONE
            if (!isChecked) {
                pantryUpdateRadioGroup.clearCheck()
                cancelReminder()
            }
        }

        // RadioGroup listener for pantry update reminders
        pantryUpdateRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.monthlyRadioButton -> {
                    cancelReminder()
                    scheduleMonthlyReminder()
                    Toast.makeText(this, "Monthly reminder set", Toast.LENGTH_SHORT).show()
                }
                R.id.weeklyRadioButton -> {
                    cancelReminder()
                    scheduleWeeklyReminder()
                    Toast.makeText(this, "Weekly reminder set", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Click listener for profile image
        profileImageView.setOnClickListener {
            showImagePickerDialog()
        }

        // Click listener for saving profile
        saveProfileButton.setOnClickListener {
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show()
        }

        // Back button logic
        val backButton: ImageView = findViewById(R.id.btnback)
        backButton.setOnClickListener {
            finish() // End this activity and return to the previous one
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
                    profileImageView.setImageBitmap(bitmap)
                }
                REQUEST_GALLERY -> {
                    imageUri = data?.data
                    profileImageView.setImageURI(imageUri)
                }
            }
        }
    }

    private fun scheduleMonthlyReminder() {
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY * 30
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, AlarmManager.INTERVAL_DAY * 30, pendingIntent)
    }

    private fun scheduleWeeklyReminder() {
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY * 7
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, AlarmManager.INTERVAL_DAY * 7, pendingIntent)
    }

    private fun cancelReminder() {
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminderChannel",
                "Pantry Reminder Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java) // Corrected line
            notificationManager.createNotificationChannel(channel)
        }
    }
}
