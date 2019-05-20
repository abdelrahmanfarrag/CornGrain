package com.example.corngrain.data.fcm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationRecevier : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val receivedOpenAt = intent?.getStringExtra("openAt")
        val receivedAlarm = intent?.getStringExtra("alarm")
        Toast.makeText(context, receivedOpenAt, Toast.LENGTH_SHORT).show()

        if (receivedAlarm != null) {
            val pref =
                context?.getSharedPreferences("local_notification_data", Context.MODE_PRIVATE)
            val notificationTitle = pref?.getString("title", "No data")
            val notificationContent = pref?.getString("content", "No Data")
            val alarmNotificationHelper = AlarmNotificationHelper(context!!)
            if (notificationContent != null && notificationTitle != null) {
                val notificationCompat =
                    alarmNotificationHelper.buildNotification(
                        notificationTitle,
                        notificationContent
                    )
                alarmNotificationHelper.getNotificationManager()
                    .notify(1, notificationCompat.build())
            }
        }
    }
}