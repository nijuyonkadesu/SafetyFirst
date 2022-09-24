package com.njk.safetyfirst

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO: NotifListener, Broadcast Receiver, Notif Listener Service, on notif posted getNotifcations(), sendBroadcast(msgrcv)
        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf("Manifest.permission.RECEIVE_SMS"), 1000)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permisson Granted 👍", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Give SMS Permissions bro 😿", Toast.LENGTH_LONG).show()
            finish()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun parseSms(){
        // TODO: https://www.youtube.com/watch?v=y-UXGMZk92E
    }
}

// TODO: info adapter https://developers.google.com/codelabs/maps-platform/maps-platform-101-android?authuser=0#6 
// TODO: get vicinity from google maps https://developers.google.com/maps/documentation/places/android-sdk/place-details