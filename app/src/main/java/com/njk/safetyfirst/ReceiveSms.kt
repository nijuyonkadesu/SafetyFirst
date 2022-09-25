package com.njk.safetyfirst

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.njk.safetyfirst.SmsLocation.latitude
import com.njk.safetyfirst.SmsLocation.longitude
import java.util.regex.Pattern

class ReceiveSms : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
//        Toast.makeText(context, "Yo working, yes. now cry.", Toast.LENGTH_SHORT).show()
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            val bundle: Bundle? = intent.getExtras()

            if (bundle!=null){
                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (message in smsMessages) {
//                    Toast.makeText(context, "Message from ${message.displayOriginatingAddress} : body ${message.messageBody}", Toast.LENGTH_SHORT).show()
                    val extractedLocation = message.messageBody
                    val pattern = Pattern.compile("lat/long: \\(([0-9.]+),([0-9.]+)\\)$")
                    val matcher = pattern.matcher(extractedLocation)

                    if (matcher.matches()) {
                        latitude = matcher.group(1)!!.toDouble()
                        longitude = matcher.group(2)!!.toDouble()
                        Toast.makeText(context, "Message from ${message.displayOriginatingAddress} : lat/long: $SmsLocation.latitude / $longitude", Toast.LENGTH_LONG).show()
                        val mapIntent = Intent(context, MapActivity::class.java)
                        mapIntent.putExtra("lat", latitude)
                        mapIntent.putExtra("long", longitude)
                        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(context, mapIntent, null)
                    }
                }
            }
        }
    }
}

// Reference: https://www.apriorit.com/dev-blog/227-handle-sms-on-android