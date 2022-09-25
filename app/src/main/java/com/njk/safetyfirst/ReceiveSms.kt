package com.njk.safetyfirst

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast

class ReceiveSms : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
//        Toast.makeText(context, "Yo working, yes. now cry.", Toast.LENGTH_SHORT).show()
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            val bundle: Bundle? = intent.getExtras()
//            val Messages = mutableListOf<String>()
//            var messageFrom: String

            if (bundle!=null){
                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (message in smsMessages) {
                    Toast.makeText(context, "Message from ${message.displayOriginatingAddress} : body ${message.messageBody}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}

// Reference: https://www.apriorit.com/dev-blog/227-handle-sms-on-android