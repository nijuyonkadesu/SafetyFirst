package com.njk.safetyfirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO: NotifListener, Broadcast Receiver, Notif Listener Service, on notif posted getNotifcations(), sendBroadcast(msgrcv)
    }

    fun parseSms(){
        // TODO: https://www.youtube.com/watch?v=y-UXGMZk92E
    }
}

// TODO: info adapter https://developers.google.com/codelabs/maps-platform/maps-platform-101-android?authuser=0#6 
// TODO: get vicinity from google maps https://developers.google.com/maps/documentation/places/android-sdk/place-details