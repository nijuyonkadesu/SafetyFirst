package com.njk.safetyfirst

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern

class ReceiveSms : BroadcastReceiver() {
    var latitude = 0.0
    var longitude = 0.0

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
                        Toast.makeText(context, "Message from ${message.displayOriginatingAddress} : lat/long: $latitude / $longitude", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

// Reference: https://www.apriorit.com/dev-blog/227-handle-sms-on-android