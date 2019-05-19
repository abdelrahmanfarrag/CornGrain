package com.example.corngrain.data.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.corngrain.R
import com.example.corngrain.ui.main.MainActivity


const val ALARM_CHANNEL_ID = "alarm"
const val ALRAM_CHANNEL_NAME = "Today Alarm"

class AlarmNotificationHelper(private val contextBase: Context) : ContextWrapper(contextBase) {

    private lateinit var notificationManager: NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            createAlarmNotificationChannel()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAlarmNotificationChannel() {
        val channel = NotificationChannel(
            ALARM_CHANNEL_ID,
            ALRAM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        getNotificationManager().createNotificationChannel(channel)

    }

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

        return NotificationCompat.Builder(applicationContext, ALARM_CHANNEL_ID)
            .setContentTitle(title)
            .setContentIntent(pendingActionReceiver)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_fire)
    }
}