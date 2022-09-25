package com.njk.safetyfirst

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.njk.safetyfirst.SmsLocation.latitude
import com.njk.safetyfirst.SmsLocation.longitude
import java.util.regex.Pattern

const val CHANNEL_ID = 6969
const val NAME = "emergency_map"
const val NOTIFICATION_ID = 69440

class ReceiveSms : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED")){
            val bundle: Bundle? = intent.extras

            if (bundle!=null){
                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (message in smsMessages) {
                    val extractedLocation = message.messageBody
                    val pattern = Pattern.compile("lat/long: \\(([0-9.]+),([0-9.]+)\\)$")
                    val matcher = pattern.matcher(extractedLocation)

                    if (matcher.matches()) {
                        latitude = matcher.group(1)!!.toDouble()
                        longitude = matcher.group(2)!!.toDouble()
                        Toast.makeText(context, "Message from ${message.displayOriginatingAddress} : lat/long: $SmsLocation.latitude / $longitude", Toast.LENGTH_LONG).show()

//                        Should create notification manager as soon as the app starts?
                        val descriptionText = "Emergency map notification"
                        val importance = NotificationManager.IMPORTANCE_HIGH
                        val channel = NotificationChannel(CHANNEL_ID.toString(), NAME, importance).apply {
                            description = descriptionText
                            enableLights(true)
                            setShowBadge(true)
                        }

                        val notificationManager: NotificationManager =
                            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)

                        val mapIntent = Intent(context, MapActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, mapIntent, PendingIntent.FLAG_IMMUTABLE)

                        val builder = NotificationCompat.Builder(context, CHANNEL_ID.toString())
                            .setSmallIcon(R.drawable.important)
                            .setContentTitle("Alert ! Emergency")
                            .setContentText("Accident! your close one felt with an accident: ${message.displayOriginatingAddress}")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)

                        notificationManager.notify(NOTIFICATION_ID, builder.build())
                    }
                }
            }
        }
    }
}

// Reference: https://www.apriorit.com/dev-blog/227-handle-sms-on-android

//                        val mapIntent = Intent(context, MapActivity::class.java)
//                        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        mapIntent.putExtra("lat", latitude)
//                        mapIntent.putExtra("long", longitude)
//                        startActivity(context, mapIntent, null)

/*
A channel Id can be any string , it is too big to discuss in comments, but it is used to
separate your notifications into categories so that the user can disable what he thinks is not
important to him rather than blocking all notifications from your app
*/