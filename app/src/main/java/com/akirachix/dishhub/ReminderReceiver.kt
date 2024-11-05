package com.akirachix.dishhub

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.akirachix.dishhub.R


class ReminderReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val title = intent.getStringExtra("notification_title") ?: "Reminder"
        val message = intent.getStringExtra("notification_message") ?: "Time to check your pantry!"


        val notification = NotificationCompat.Builder(context, "PantryReminderChannel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()


        notificationManager.notify(1, notification)
    }
}

