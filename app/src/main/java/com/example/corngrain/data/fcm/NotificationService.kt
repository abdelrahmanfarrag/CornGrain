package com.example.corngrain.data.fcm

import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.corngrain.CHANNEL_ID
import com.example.corngrain.R
import com.example.corngrain.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        createNotificationPanel()
    }

    private fun createNotificationPanel() {
        val notificationAction = Intent(applicationContext, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, notificationAction, 0
        )

        val broadcastIntent = Intent(applicationContext, NotificationRecevier::class.java)
        broadcastIntent.putExtra("openAt", "MOVIES")
        val pendingActionReceiver = PendingIntent.getBroadcast(
            applicationContext,
            0,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_fire)
            .setContentIntent(pendingIntent)
            .setContentTitle("Hi man")
            .setContentText("Welcome to notiifcation part of corn grain app")
            .setAutoCancel(true)
            .addAction(R.drawable.ic_fire, "Toast", pendingActionReceiver)
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(1, notification)

    }
}