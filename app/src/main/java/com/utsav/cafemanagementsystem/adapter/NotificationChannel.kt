package com.utsav.cafemanagementsystem.adapter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationChannels(val context: Context) {
    val CHANNEL_1: String= "Channel1"
    val CHANNEL_2: String= "Channel2"

    fun createNotificationChannels(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel1= NotificationChannel(CHANNEL_1,"Channel1",NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description="This is channel 1"

            val channel2= NotificationChannel(CHANNEL_2,"Channel2",NotificationManager.IMPORTANCE_LOW
            )
            channel2.description="This is channel 2"
            val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }
}
