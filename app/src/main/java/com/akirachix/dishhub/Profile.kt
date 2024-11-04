

package com.akirachix.dishhub


import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.util.*


class Profile : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmManager: AlarmManager
    private lateinit var reminderSwitch: Switch
    private lateinit var pantryUpdateRadioGroup: RadioGroup
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var txtOn: TextView
    private lateinit var txtOff: TextView
    private lateinit var monthlyRadioButton: RadioButton
    private lateinit var weeklyRadioButton: RadioButton
    private lateinit var backButton: ImageView


    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY = 2
    private var imageUri: Uri? = null
    private val CHANNEL_ID = "PantryReminderChannel"
    private val NOTIFICATION_ID = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        initializeViews()
        loadUserData()
        setupListeners()
        createNotificationChannel()
        loadReminderSettings()
    }


    private fun initializeViews() {
        profileImageView = findViewById(R.id.profileImageView)
        nameTextView = findViewById(R.id.nameTextView)
        pantryUpdateRadioGroup = findViewById(R.id.pantryUpdateRadioGroup)
        reminderSwitch = findViewById(R.id.reminderSwitch)
        txtOn = findViewById(R.id.txton)
        txtOff = findViewById(R.id.txtoff)
        monthlyRadioButton = findViewById(R.id.monthlyRadioButton)
        weeklyRadioButton = findViewById(R.id.weeklyRadioButton)
        backButton = findViewById(R.id.btnback)
    }


    private fun loadUserData() {
        val currentUserEmail = sharedPreferences.getString("current_user_email", "") ?: ""
        if (currentUserEmail.isNotEmpty()) {
            val firstName = sharedPreferences.getString("${currentUserEmail}_firstName", "") ?: ""
            val lastName = sharedPreferences.getString("${currentUserEmail}_lastName", "") ?: ""
            val fullName = "$firstName $lastName"


            nameTextView.text = fullName


            // Load profile image
            val profileImagePath = sharedPreferences.getString("${currentUserEmail}_profile_image", null)
            if (profileImagePath != null) {
                val imageFile = File(profileImagePath)
                if (imageFile.exists()) {
                    profileImageView.setImageURI(Uri.fromFile(imageFile))
                }
            }
        }
    }


    private fun setupListeners() {
        profileImageView.setOnClickListener {
            showImagePickerDialog()
        }


        reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            handleReminderSwitchChange(isChecked)
        }


        pantryUpdateRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            handleReminderFrequencyChange(checkedId)
        }


        backButton.setOnClickListener {
            finish()
        }
    }


    private fun handleReminderSwitchChange(isChecked: Boolean) {
        pantryUpdateRadioGroup.visibility = if (isChecked) RadioGroup.VISIBLE else RadioGroup.GONE


        if (!isChecked) {
            pantryUpdateRadioGroup.clearCheck()
            cancelReminder()
        }


        sharedPreferences.edit()
            .putBoolean("reminder_enabled", isChecked)
            .apply()
    }


    private fun handleReminderFrequencyChange(checkedId: Int) {
        when (checkedId) {
            R.id.monthlyRadioButton -> {
                cancelReminder()
                scheduleMonthlyReminder()
                showToast("Monthly reminder set")
                saveReminderPreference("monthly")
            }
            R.id.weeklyRadioButton -> {
                cancelReminder()
                scheduleWeeklyReminder()
                showToast("Weekly reminder set")
                saveReminderPreference("weekly")
            }
        }
    }


    private fun saveReminderPreference(frequency: String) {
        sharedPreferences.edit()
            .putString("reminder_frequency", frequency)
            .apply()
    }


    private fun loadReminderSettings() {
        val isReminderEnabled = sharedPreferences.getBoolean("reminder_enabled", false)
        reminderSwitch.isChecked = isReminderEnabled
        pantryUpdateRadioGroup.visibility = if (isReminderEnabled) RadioGroup.VISIBLE else RadioGroup.GONE


        if (isReminderEnabled) {
            when (sharedPreferences.getString("reminder_frequency", "")) {
                "monthly" -> monthlyRadioButton.isChecked = true
                "weekly" -> weeklyRadioButton.isChecked = true
            }
        }
    }


    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        android.app.AlertDialog.Builder(this)
            .setTitle("Choose Profile Photo")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkAndRequestCameraPermission()
                    1 -> checkAndRequestGalleryPermission()
                }
            }
            .show()
    }


    private fun checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            openCamera()
        }
    }


    private fun checkAndRequestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_GALLERY
            )
        } else {
            openGallery()
        }
    }


    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    private fun openGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also { intent ->
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let { saveProfileImage(it) }
                }
                REQUEST_GALLERY -> {
                    data?.data?.let { uri ->
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        saveProfileImage(bitmap)
                    }
                }
            }
        }
    }


    private fun saveProfileImage(bitmap: Bitmap) {
        val currentUserEmail = sharedPreferences.getString("current_user_email", "") ?: ""
        if (currentUserEmail.isNotEmpty()) {
            val filename = "profile_${currentUserEmail}.jpg"
            val file = File(filesDir, filename)


            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }


            sharedPreferences.edit()
                .putString("${currentUserEmail}_profile_image", file.absolutePath)
                .apply()


            profileImageView.setImageBitmap(bitmap)
            showToast("Profile picture updated")
        }
    }


    private fun scheduleMonthlyReminder() {
        scheduleReminder(AlarmManager.INTERVAL_DAY * 30)
    }


    private fun scheduleWeeklyReminder() {
        scheduleReminder(AlarmManager.INTERVAL_DAY * 7)
    }


    private fun scheduleReminder(interval: Long) {
        val intent = Intent(this, ReminderReceiver::class.java).apply {
            putExtra("notification_title", "Pantry Update Reminder")
            putExtra("notification_message", "Time to update your pantry inventory!")
        }


        val pendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + interval,
            interval,
            pendingIntent
        )
    }


    private fun cancelReminder() {
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pantry Reminder"
            val descriptionText = "Channel for Pantry Update Reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }


            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
