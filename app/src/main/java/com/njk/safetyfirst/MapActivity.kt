package com.njk.safetyfirst

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import com.njk.safetyfirst.SmsLocation.latitude
import com.njk.safetyfirst.SmsLocation.longitude
import com.njk.safetyfirst.databinding.ActivityMapBinding

object SmsLocation {
    var latitude: Double = 0.0
    var longitude: Double = 0.0
}

class MapActivity :AppCompatActivity() {

    var emergencyLocation: LatLng = LatLng(0.0, 0.0)
    private var circle: Circle? = null

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        latitude = intent?.extras?.getString("lat")!!.toDouble()
//        longitude = intent?.extras?.getString("long")!!.toDouble()
        emergencyLocation = LatLng(latitude, longitude)
        Log.d("intent", "intent received $latitude, $longitude")

        // TODO: fix findFragmentById cannot detect view
        val mapFragment = (supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment)
        lifecycleScope.launchWhenCreated {
            // get map
            val googleMap = mapFragment.awaitMap()
            // wait for map to finish loading
            googleMap.awaitMapLoad()
            val bounds = LatLngBounds.builder()
            bounds.include(emergencyLocation)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
            marker(googleMap)
        }
    }

    private fun marker(googleMap: GoogleMap) {
        val mMap = googleMap

        mMap.addMarker(
            MarkerOptions()
                .position(emergencyLocation)
                .title("Marker in Emergency!")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(emergencyLocation))
        addCircle(mMap, emergencyLocation)
    }
    private fun addCircle(googleMap: GoogleMap, item: LatLng) {
        circle?.remove()
        circle = googleMap.addCircle(
            CircleOptions()
                .center(item)
                .radius(50.0)
                .fillColor(ContextCompat.getColor(this, R.color.red_translucent))
                .strokeColor(ContextCompat.getColor(this, R.color.red))
        )
    }
}