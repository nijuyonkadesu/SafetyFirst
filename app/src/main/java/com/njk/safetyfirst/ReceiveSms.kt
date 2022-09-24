package com.njk.safetyfirst

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceiveSms : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val toast = Toast.makeText(context, "Yo working, yes. now cry.", Toast.LENGTH_SHORT)
        toast.show()
    }
}