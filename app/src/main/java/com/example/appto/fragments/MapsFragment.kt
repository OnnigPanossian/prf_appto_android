package com.example.appto.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.appto.R
import com.example.appto.databinding.ActivityMainBinding
import com.example.appto.databinding.FragmentMapsBinding
import com.example.appto.models.Parking
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.ParkingViewModel
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
import com.google.android.gms.tasks.CancellationTokenSource


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val userLocation = Location("")
    private var parkings = mutableListOf<Parking>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val args: MapsFragmentArgs by navArgs()
    private lateinit var parkingViewModel: ParkingViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        parkingViewModel = ViewModelProvider(this)[ParkingViewModel::class.java]

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        requestLocationPermission()

        sessionManager = SessionManager(context!!)

        if (sessionManager.isRentalInProgress()) {
            binding.imageFilter.visibility = View.GONE
            binding.tvRentalInProgress.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }

        binding.imageFilter.setOnClickListener {
            val dialog = FiltersDialogFragment()
            dialog.show(childFragmentManager, "FiltersDialogFragment")
        }

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
        val geocoder = Geocoder(context)

        val icon = getIcon()

        // Ubicamos cada parking en el mapa de acuerdo a su lat y long
        parkingViewModel.filterParkings(args.filters)
        parkingViewModel.parkings.observe(this, { parkings ->
            parkings.forEach { p ->
                val position = LatLng(p.lat, p.long)
                mMap.addMarker(MarkerOptions().position(position).title(p.name).icon(icon))
            }

            mMap.setOnMarkerClickListener { marker ->
                val currentParking = parkings.find { p -> p.name == marker.title }
                val dialog = ParkingDialogFragment()
                val bundle = Bundle()
                bundle.putString("name", currentParking!!.name)
                bundle.putString(
                    "address",
                    geocoder.getFromLocation(
                        currentParking.lat,
                        currentParking.long,
                        1
                    )[0].getAddressLine(0).toString()
                )

                val results = FloatArray(1)

                Location.distanceBetween(
                    userLocation.latitude,
                    userLocation.longitude,
                    currentParking.lat,
                    currentParking.long,
                    results
                )
                bundle.putString(
                    "distance",
                    "${"Estás a " + String.format("%.2f", results[0] / 1000)} kms de distancia"
                )

                bundle.putString("id", currentParking.id)
                dialog.arguments = bundle
                dialog.show(childFragmentManager, "ParkingDialogFragment")
                true
            }
        })


        // Add a marker in user Location
        val user = LatLng(userLocation.latitude, userLocation.longitude)
        // mMap.addMarker(MarkerOptions().position(user).title("Buenos Aires"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 12.0F))

        if (checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        // Move Location Button to bottom
        val locationButton =
            (binding.root.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(
                Integer.parseInt("2")
            )
        val rlp = locationButton.layoutParams as (RelativeLayout.LayoutParams)
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 30, 300);
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