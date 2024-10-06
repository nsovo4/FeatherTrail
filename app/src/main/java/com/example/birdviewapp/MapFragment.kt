package com.example.birdviewapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.MapboxOptions
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationObserver
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.locationcomponent.location


class MapFragment : Fragment(), PermissionsListener {

    lateinit var mapView: MapView
    private lateinit var zoomInBtn: ImageView
    private lateinit var zoomOutBtn: ImageView
    private lateinit var permissionsManager: PermissionsManager
    val locationService : LocationService = LocationServiceFactory.getOrCreate()
    var locationProvider: DeviceLocationProvider? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set the Mapbox access token
        MapboxOptions.accessToken = "pk.eyJ1IjoibnNvdm8tbWJvd2VuaSIsImEiOiJjbTF3aGNleWwwamhxMmpxd2c4enhrcmk2In0.duJeT3b2A-irMmuyGdBG6Q"

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Initialize views with safe calls to avoid null issues
        mapView = view.findViewById(R.id.mapView) as MapView
        zoomInBtn = view.findViewById(R.id.zoomInBtn) as ImageView
        zoomOutBtn = view.findViewById(R.id.zoomOutBtn) as ImageView

        if (PermissionsManager.areLocationPermissionsGranted(requireContext())) {

            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .center(Point.fromLngLat(28.187, -25.74592))  // Center to the required location
                    .pitch(0.0)
                    .zoom(10.0)
                    .bearing(180.0)
                    .build()
            )

            mapView.location.enabled = true

            val request = LocationProviderRequest.Builder()
                .interval(IntervalSettings.Builder().interval(0L).minimumInterval(0L).maximumInterval(0L).build())
                .displacement(0F)
                .accuracy(AccuracyLevel.HIGHEST)
                .build();

            val result = locationService.getDeviceLocationProvider(request)
            if (result.isValue) {
                locationProvider = result.value!!
            } else {
                Log.e("hello", "Failed to get device location provider")
            }

            val locationObserver = object: LocationObserver {
                override fun onLocationUpdateReceived(locations: MutableList<Location>) {
                    Log.e("hello", "Location update received: " + locations)

                }
            }
            locationProvider?.addLocationObserver(locationObserver)

        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(requireActivity())
        }

        // Set zoom in button listener
        zoomInBtn.setOnClickListener {
            val camState = mapView.getMapboxMap().cameraState
            val currentZoom = camState.zoom
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder().zoom(currentZoom + 1).build()
            )
        }

        // Set zoom out button listener
        zoomOutBtn.setOnClickListener {
            val camState = mapView.getMapboxMap().cameraState
            val currentZoom = camState.zoom
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder().zoom(currentZoom - 1).build()
            )
        }

        return view
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionResult(granted: Boolean) {
        TODO("Not yet implemented")
    }
}


