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
            val alarmNotificationHelper = AlarmNotificationHelper(context!!)
            val notificationCompat =
                alarmNotificationHelper.buildNotification("Hello", "Alarm is working fine")
            alarmNotificationHelper.getNotificationManager().notify(1, notificationCompat.build())
        }
    }
}