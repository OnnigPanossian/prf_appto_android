package com.example.appto.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.appto.R
import com.example.appto.databinding.FragmentMapsBinding
import com.example.appto.models.Parking
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val userLocation = Location("")
    private var parkings = mutableListOf<Parking>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        showAvailableParkings()
        requestLocationPermission()
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.i("Location: ", location.toString())
            if (location != null) {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
                setupMap()
            }
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun requestLocationPermission() {
        if (checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getUserLocation()
        } else {
            val permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionsArray, 1000)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when {
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                getUserLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showLocationPermissionRationaleDialog()
            }
            else -> {
                childFragmentManager.beginTransaction().remove(this).commit()
            }
        }
    }

    private fun showLocationPermissionRationaleDialog() {
        val dialog = AlertDialog.Builder(requireActivity().applicationContext)
            .setTitle("Necesitas permiso de ubicación")
            .setMessage("Acepta el permiso para ubicar los estacionamientos")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1000
                )
            }.setNegativeButton("No") { _, _ ->
                childFragmentManager.beginTransaction().remove(this).commit()
            }
        dialog.show()
    }

    private fun showAvailableParkings() {
        // TODO: TOMAR DESDE EL BACKEND
        parkings.add(Parking("Alcorta", -34.601199723075204, -58.39422029816545, null))
        parkings.add(Parking("Bombonera Parking", -34.599910362624726, -58.40408009840915, null))
        parkings.add(Parking("Cordoba Garage", -34.620283237399164, -58.4576767673698, null))
        parkings.add(Parking("Tilin & Co.", -34.551363857592044, -58.54742150278589, null))
        parkings.add(Parking("Le cuidamos su carro", -34.544272198662625, -58.46639946081241, null))
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val icon = getIcon()
        parkings.forEach { p ->
            val position = LatLng(p.lat, p.long)
            mMap.addMarker(MarkerOptions().position(position).title(p.name).icon(icon))
        }
        // Add a marker in user Location
        val user = LatLng(userLocation.latitude, userLocation.longitude)
        mMap.addMarker(MarkerOptions().position(user).title("Buenos Aires"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 12.0F))
    }


    // Transforms a drawable into a bitmap
    private fun getIcon(): BitmapDescriptor {
        val drawable =
            ContextCompat.getDrawable(context!!, R.drawable.ic_parking)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth ?: 0,
            drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}