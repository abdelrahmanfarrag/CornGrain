package com.example.corngrain.data.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.corngrain.CHANNEL_LOCAL_ID
import com.example.corngrain.R
import com.example.corngrain.ui.main.MainActivity


class AlarmNotificationHelper(private val contextBase: Context) : ContextWrapper(contextBase) {

    private lateinit var notificationManager: NotificationManager

    fun getNotificationManager(): NotificationManager {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager
    }

    fun buildNotification(title: String, content: String): NotificationCompat.Builder {
        val homeIntent = Intent(contextBase, MainActivity::class.java)
        val pendingActionReceiver = PendingIntent.getActivity(
            applicationContext,
            0,
            homeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(applicationContext, CHANNEL_LOCAL_ID)
            .setContentTitle(title)
            .setContentIntent(pendingActionReceiver)
            .setContentText(content)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_fire)
    }
}