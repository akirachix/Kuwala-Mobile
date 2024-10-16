package com.akirachix.dishhub


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.akirachix.dishhub.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val builder = NotificationCompat.Builder(context, "reminderChannel")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Pantry Reminder")
            .setContentText("Don't forget to update your pantry!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, builder.build())
    }
}