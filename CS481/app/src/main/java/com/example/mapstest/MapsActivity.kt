package com.example.mapstest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.TextView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import com.example.appDatabase
//import com.example.entities.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
//end of Google API imports ^^^
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
//end of Spinner imports

// Custom location manager
import com.example.mapstest.location.LocationManager

// Android permissions and location related imports
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.location.Location
import android.view.Menu
import android.view.MenuItem

// Google Maps related imports
import com.google.android.gms.maps.model.PolylineOptions

// Custom Repository and Entity imports
import com.example.mapstest.PolylineRepository.route12Coordinates
import com.example.mapstest.PolylineRepository.route13Coordinates
import com.example.mapstest.PolylineRepository.route14Coordinates
import com.example.mapstest.PolylineRepository.route15Coordinates
import com.example.mapstest.PolylineRepository.route16Coordinates

// User Interface related imports
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar

// Google Places API related imports
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.example.UserRepository
import com.example.mapstest.adapters.CustomDropdownAdapter


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var savePinButton: Button
    private lateinit var routeSpinner: Spinner // Route spinner
    private var selectedMarker: Marker? = null
    private lateinit var busStopsRepository: BusStopsRepository
    private val pinnedLocations = mutableListOf<String>() // Mutable list to hold pinned locations
    //private lateinit var deletePinButton: Button
    private lateinit var showPinnedLocationsButton: ImageView // Change from Button to ImageView
    private lateinit var pinnedLocationsSpinner: CustomSpinner
    private lateinit var userRepository: UserRepository
    var lastSelectedPosition = AdapterView.INVALID_POSITION
    var isSelectionChangeInProgress = false






    private var pinnedLocationsSpinnerInitialized = false





    private lateinit var searchView: SearchView
    private lateinit var placesClient: PlacesClient



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_maps)

        Places.initialize(applicationContext, "AIzaSyDUHBmJ8rS1DhC8sqV9LnJJ0bv2I12iyXo")
        placesClient = Places.createClient(this)

        savePinButton = findViewById(R.id.save_pin_button)
        savePinButton.visibility = View.GONE // Hide button initially


        busStopsRepository = BusStopsRepository
        userRepository = UserRepository(applicationContext)



        savePinButton.setOnClickListener {
            selectedMarker?.let { marker ->
                onSavePinClick(marker)
            }
        }

        showPinnedLocationsButton = findViewById(R.id.button_show_pinned_locations)
        pinnedLocationsSpinner = findViewById(R.id.spinner_pinned_locations)
        pinnedLocationsSpinner.visibility = View.GONE

        showPinnedLocationsButton.setOnClickListener {
            if (pinnedLocations.isNotEmpty()) {
                showPinnedLocationsButton.visibility = View.GONE
                pinnedLocationsSpinner.visibility = View.VISIBLE
                pinnedLocationsSpinner.performClick()
            }else{
                Toast.makeText(this@MapsActivity, "No saved pins", Toast.LENGTH_SHORT).show()

            }
        }

        // Set up spinner item selection listener to hide it when an item is selected
        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                pinnedLocationsSpinner.visibility = View.GONE
                showPinnedLocationsButton.visibility = View.VISIBLE
                if (position >= 0) {
                    val selectedLocation = pinnedLocations[position]
                    moveToPinnedLocation(selectedLocation)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                pinnedLocationsSpinner.visibility = View.GONE
                showPinnedLocationsButton.visibility = View.VISIBLE
            }
        }

        // Set the OnItemSelectedListener
        pinnedLocationsSpinner.onItemSelectedListener = onItemSelectedListener

        // Set up custom spinner event listener to handle dismiss event
        pinnedLocationsSpinner.onSpinnerEventsListener = object : CustomSpinner.OnSpinnerEventsListener {
            override fun onSpinnerClosed() {
                pinnedLocationsSpinner.visibility = View.GONE
                showPinnedLocationsButton.visibility = View.VISIBLE
            }
        }




        // Initialize the SearchView
        Places.initialize(applicationContext, "AIzaSyDUHBmJ8rS1DhC8sqV9LnJJ0bv2I12iyXo")
        placesClient = Places.createClient(this)
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val modifiedQuery = "Ellensburg $it" // Modify the query to include "Ellensburg"
                    searchLocation(modifiedQuery)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            // Function to handle location search
            fun searchLocation(query: String) {
                val request = FindAutocompletePredictionsRequest.builder()
                    .setQuery(query)
                    .build()

                placesClient.findAutocompletePredictions(request)
                    .addOnSuccessListener { response ->
                        for (prediction in response.autocompletePredictions) {
                            val placeId = prediction.placeId
                            val placeFields = listOf(Place.Field.LAT_LNG, Place.Field.NAME)

                            val placeRequest = FetchPlaceRequest.builder(placeId, placeFields).build()
                            placesClient.fetchPlace(placeRequest)
                                .addOnSuccessListener { placeResponse ->
                                    val place = placeResponse.place
                                    val latLng = place.latLng
                                    latLng?.let {
                                        //mMap.clear()
                                        val marker = mMap.addMarker(
                                            MarkerOptions().position(it).title(place.name)

                                        )
                                        mMap.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(
                                                it,
                                                15f
                                            )
                                        )
                                        // Check distance to Ellensburg and display info if within 1 mile
                                        val ellensburg = LatLng(47.0073, -120.5370)
                                        val distance = FloatArray(1)
                                        Location.distanceBetween(ellensburg.latitude, ellensburg.longitude,
                                            it.latitude, it.longitude, distance)
                                        if (distance[0] < 1609.34) { // If distance is less than 1 mile
                                            Snackbar.make(findViewById(android.R.id.content), "Get Walking Directions to This Location", Snackbar.LENGTH_LONG).show()
                                        }
                                    }
                                }
                        }
                    }
            }

        })
        // Initialization of LocationManager goes here after permissions check
        locationManager = LocationManager(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize spinners
        routeSpinner = findViewById(R.id.spinner_routes)
        pinnedLocationsSpinner = findViewById(R.id.spinner_pinned_locations)
        pinnedLocationsSpinner.visibility = View.VISIBLE // Ensure the spinner is visible


        // Set up the Spinner
        val spinner: Spinner = findViewById(R.id.spinner_routes)
        ArrayAdapter.createFromResource(
            this,
            R.array.route_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            routeSpinner.adapter = adapter
        } //Basically this code just gets the layout of the spinner from the xml file and then gets it ready to display in the activity

        // Spinner item selection listener
        routeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get selected item
                val selectedRoute = when (position) {
                    0 -> 12
                    1 -> 13
                    2 -> 14
                    3 -> 15
                    4 -> 16
                    else -> -1
                }

                if (selectedRoute != -1) filterMarkersByRoute(selectedRoute)
                if (selectedRoute != -1) filterLinesByRoute(selectedRoute)
            }//end of onItemSelect function

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }//end of event listener for when an item in the spinner is seleted


        setupSpinner()
        updateDropdownList()
    }//end of onCreate function



    //variable to track the markers
    private val markers = mutableListOf<Marker>()
    private val polylines = mutableListOf<Polyline>()

    // This function goes through all markers and sets their visibility based on the route number
    private fun filterMarkersByRoute(routeNumber: Int) {
        markers.forEach { marker ->
            marker.isVisible = (marker.tag as? Int) == routeNumber
        }
    }//end of func that filters the markers by route

    // This function does the same as above, but for routeLines
    private fun filterLinesByRoute(routeNumber: Int) {
        polylines.forEach { polyline ->
            polyline.isVisible = (polyline.tag as? Int) == routeNumber
        }
    }//end of func that filters the markers by route

    //Creating toolbar/dropdown menu on the top to test saved pin locations!
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        lifecycleScope.launch(Dispatchers.IO) {
            val db = appDatabase.getInstance(applicationContext)
            val pinnedLocations = db.userDataDao().getAllUserData()
            withContext(Dispatchers.Main) {
                val pinnedLocationsMenu = menu?.findItem(R.id.action_pinned_locations)?.subMenu
                pinnedLocationsMenu?.clear()
                pinnedLocations.forEach { userData ->
                    userData.pinnedLocations.filterNotNull().forEach { pinnedLocation ->
                        pinnedLocationsMenu?.add(pinnedLocation)?.setOnMenuItemClickListener {
                            moveToPinnedLocation(pinnedLocation)
                            true
                        }
                    }
                }
            }
        }
        return true
    }


    //end of tool bar creation


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Ellensburg, WA, and move the camera
        val ellensburg = LatLng(47.0073, -120.5370)
        mMap.addMarker(MarkerOptions().position(ellensburg).title("Test Location: Ellensburg"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ellensburg, 12.0f))

        mMap.setOnMarkerClickListener { marker ->
            val distance = FloatArray(1)
            Location.distanceBetween(ellensburg.latitude, ellensburg.longitude,
                marker.position.latitude, marker.position.longitude, distance)
            if (distance[0] < 1609.34) { // Distance less than 1 mile
                // Code to display a popup or modify the InfoWindow to show walking directions
                Toast.makeText(this, "Get Walking Directions To This Stop", Toast.LENGTH_LONG).show()
            }
            savePinButton.visibility = View.VISIBLE
            //deletePinButton.visibility = View.VISIBLE
            selectedMarker = marker
            false // We return false to indicate that we are not consuming the event and InfoWindow should still show
        }

        // Set the custom InfoWindow adapter
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                // Default is null, return null if you want the default window frame
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate custom layout using layout inflater
                val infoView = layoutInflater.inflate(R.layout.custom_info_contents, null)
                val title = infoView.findViewById<TextView>(R.id.title)
                val snippet = infoView.findViewById<TextView>(R.id.snippet)

                title.text = marker.title
                snippet.text = marker.snippet

                return infoView
            }
        })

        mMap.setOnMapClickListener {
            savePinButton.visibility = View.GONE // Hide button when map is clicked
           // deletePinButton.visibility = View.GONE // Hide button when map is clicked

            selectedMarker = null
        }




        // ADD A LINE USING GOOGLE DEMO
        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(-0x00e577e4)
        )
        // adds points from repo
        route12Coordinates.forEach { coordinate ->
            polyline1.points = polyline1.points.plus(coordinate) // Add each coordinate to the existing polyline
        }
        polyline1.tag = 12 // Set the route number as the tag
        polylines.add(polyline1)

        // route 13
        val polyline2 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(-0x7e7ee6)
        )
        // adds points from repo
        route13Coordinates.forEach { coordinate ->
            polyline2.points = polyline2.points.plus(coordinate) // Add each coordinate to the existing polyline
        }
        polyline2.tag = 13 // Set the route number as the tag
        polylines.add(polyline2)

        val polyline3 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(-0xe37272)
        )
        // adds points from repo
        route14Coordinates.forEach { coordinate ->
            polyline3.points = polyline3.points.plus(coordinate) // Add each coordinate to the existing polyline
        }
        polyline3.tag = 14 // Set the route number as the tag
        polylines.add(polyline3)


        val polyline4 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(-0x489be0)
        )
        // adds points from repo
        route15Coordinates.forEach { coordinate ->
            polyline4.points = polyline4.points.plus(coordinate) // Add each coordinate to the existing polyline
        }
        polyline4.tag = 15 // Set the route number as the tag
        polylines.add(polyline4)

        val polyline5 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(-0xe6e662)
        )
        // adds points from repo
        route16Coordinates.forEach { coordinate ->
            polyline5.points = polyline5.points.plus(coordinate) // Add each coordinate to the existing polyline
        }
        polyline5.tag = 16 // Set the route number as the tag
        polylines.add(polyline5)





        filterLinesByRoute(15)

        //START OF BUS STOP SETUPS AND ADDITION TO MAPS

        val busStops12 = BusStopsRepository.getBusStopsForRoute(12) //Getting all of route 12's bus stops
        addBusStopsToMap(busStops12) // Adding all of route 12's bus stops to the map

        val busStops13 = BusStopsRepository.getBusStopsForRoute(13) //Getting all of route 13's bus stops
        addBusStopsToMap(busStops13) // Adding all of route 13's bus stops to the map

        val busStops14 = BusStopsRepository.getBusStopsForRoute(14) //Getting all of route 14's bus stops
        addBusStopsToMap(busStops14) // Adding all of route 14's bus stops to the map

        val busStops15 = BusStopsRepository.getBusStopsForRoute(15) //Getting all of route 15's bus stops
        addBusStopsToMap(busStops15) // Adding all of route 15's bus stops to the map

        val busStops16 = BusStopsRepository.getBusStopsForRoute(16) //Getting all of route 13's bus stops
        addBusStopsToMap(busStops16) // Adding all of route 13's bus stops to the map

        //END OF BUS STOP SETUPS AND ADDITIONS TO MAP ^^^^

        // Now filter by number to show whatever bus stops you want
        filterMarkersByRoute(15)
        //FILTER ALGORITHM ^^^

        startLocationTracking()
        //begin tracking users location

        // Enable the My Location layer if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            // Ask for permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        }
        //HANDLE ADDING THE BLUE PIN TO THE MAP ^^^^


    } //end of onMapReady func

    private fun startLocationTracking() {
        // Check for location permission before starting location updates
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {

            locationManager.startLocationUpdates()
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, start location tracking
                startLocationTracking()
            } else {
                // Permission was denied
                Toast.makeText(this, "Location permission is required to show the bus stops near you.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Stop location updates to save battery when not in use
        locationManager.stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        // Start location updates when the app comes back into the foreground, if permission is granted
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            locationManager.startLocationUpdates()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun addBusStopsToMap(busStops: List<BusStop>) {
        busStops.forEach { stop ->
            val marker = mMap.addMarker(MarkerOptions()
                .position(stop.latLng)
                .title(stop.title)
                .snippet(stop.snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(stop.color)))
            marker?.tag = stop.route
            marker?.let { markers.add(it) }
        }
    }//end of add bust stops to map function


    private fun onSavePinClick(marker: Marker) {
        Log.d("MapsActivity", "Saving pin: ${marker.title}, ${marker.position}")

        val title = marker.title ?: ""
        val userId = getCurrentUserUID()

        if(pinnedLocations.contains(marker.title)){
            Toast.makeText(this@MapsActivity, "ALREADY SAVED PIN", Toast.LENGTH_SHORT).show()


        }else {

            lifecycleScope.launch(Dispatchers.IO) {
                if (userId != -1) {
                    try {
                        val pinCount = userRepository.getPinnedLocationCount(userId)
                        if (pinCount >= 5) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    applicationContext,
                                    "You can only save up to 5 pins.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            userRepository.savePinnedLocation(userId, title)
                            Log.d("MapsActivity", "Pinned location saved for user $userId")
                            withContext(Dispatchers.Main) {
                                updateDropdownList()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("MapsActivity", "Error saving pin: ${e.message}")
                            Toast.makeText(
                                applicationContext,
                                "Error saving pin",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("MapsActivity", "User not found for id: $userId")
                        Toast.makeText(
                            applicationContext,
                            "User not found, cannot save pin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }













    // Ensure the moveToPinnedLocation method is compatible with the spinner selection
    private fun moveToPinnedLocation(locationTitle: String) {
        Log.d("MapsActivity", "moveToPinnedLocation called with title: $locationTitle")


        // Find the existing marker with the given title
        val existingMarker = markers.find { it.title == locationTitle }

        if (existingMarker != null) {
            Log.d("MapsActivity", "Marker found: ${existingMarker.title} at ${existingMarker.position}")

            val location = existingMarker.position
            val routeNumber = existingMarker.tag as? Int

            routeNumber?.let {
                selectRouteInSpinner(it) // Update the spinner to the route of the selected marker
                filterMarkersByRoute(it)
                filterLinesByRoute(it)
            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f), object : GoogleMap.CancelableCallback {
                override fun onFinish() {
                    existingMarker.showInfoWindow()
                    selectedMarker = existingMarker
                    Log.d("MapsActivity", "Camera moved to: ${existingMarker.position}")
                }

                override fun onCancel() {
                    Log.d("MapsActivity", "Camera movement cancelled")
                }
            })
        } else {
            Log.e("MapsActivity", "Marker not found for title: $locationTitle")
            searchLocationAndMove(locationTitle)
        }
    }





    private fun searchLocationAndMove(query: String) {
        val modifiedQuery = "Ellensburg $query" // Modify the query to include "Ellensburg"
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(modifiedQuery)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                if (response.autocompletePredictions.isNotEmpty()) {
                    val prediction = response.autocompletePredictions.first()
                    val placeId = prediction.placeId
                    val placeFields = listOf(Place.Field.LAT_LNG, Place.Field.NAME)

                    val placeRequest = FetchPlaceRequest.builder(placeId, placeFields).build()
                    placesClient.fetchPlace(placeRequest)
                        .addOnSuccessListener { placeResponse ->
                            val place = placeResponse.place
                            val latLng = place.latLng
                            latLng?.let {
                                Log.d("MapsActivity", "Fetched place: ${place.name} at $latLng")
                                // Do not clear the map to preserve existing routes and markers
                                val marker = mMap.addMarker(
                                    MarkerOptions().position(it).title(place.name)
                                )
                                mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        it,
                                        15f
                                    )
                                )
                                // Check distance to Ellensburg and display info if within 1 mile
                                val ellensburg = LatLng(47.0073, -120.5370)
                                val distance = FloatArray(1)
                                Location.distanceBetween(
                                    ellensburg.latitude, ellensburg.longitude,
                                    it.latitude, it.longitude, distance
                                )
                                if (distance[0] < 1609.34) { // If distance is less than 1 mile
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Get Walking Directions to This Location",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                                // Add the new marker to the markers list
                                marker?.let { markers.add(it) }
                                marker?.showInfoWindow()
                                selectedMarker = marker
                            }
                        }
                } else {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MapsActivity", "Error finding place: $exception")
                Toast.makeText(this, "Error finding location", Toast.LENGTH_SHORT).show()
            }
    }




    private fun selectRouteInSpinner(routeNumber: Int) {
        val position = when (routeNumber) {
            12 -> 0
            13 -> 1
            14 -> 2
            15 -> 3
            16 -> 4
            else -> -1
        }
        if (position != -1) {
            routeSpinner.setSelection(position)
        }
    }





    // Import statements added


    private fun setupSpinner() {
        val adapter = CustomDropdownAdapter(this, pinnedLocations) { location ->
            removePin(location)
        }
        pinnedLocationsSpinner.adapter = adapter

        pinnedLocationsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0 && pinnedLocations.isNotEmpty()) {
                    val selectedLocation = pinnedLocations[position]
                    moveToPinnedLocation(selectedLocation)
                }
                pinnedLocationsSpinner.visibility = View.GONE
                showPinnedLocationsButton.visibility = View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                pinnedLocationsSpinner.visibility = View.GONE
                showPinnedLocationsButton.visibility = View.VISIBLE
            }
        }

        pinnedLocationsSpinner.onSpinnerEventsListener = object : CustomSpinner.OnSpinnerEventsListener {
            override fun onSpinnerClosed() {
                pinnedLocationsSpinner.visibility = View.GONE
                showPinnedLocationsButton.visibility = View.VISIBLE
            }
        }
    }

    // In your activity or fragment
    private fun updateDropdownList() {
        pinnedLocations.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            val userId = getCurrentUserUID()
            val userRepository = UserRepository(applicationContext)
            val userPinnedLocations = userRepository.getPinnedLocationsForUser(userId)
            pinnedLocations.addAll(userPinnedLocations)
            removeValuesViaIteration(pinnedLocations)

            withContext(Dispatchers.Main) {
                (pinnedLocationsSpinner.adapter as CustomDropdownAdapter).notifyDataSetChanged()
                showPinnedLocationsButton.visibility = if (pinnedLocations.isEmpty()) View.GONE else View.VISIBLE
                if (pinnedLocations.isEmpty()) {
                    // Show a message when the list is empty
                    findViewById<TextView>(R.id.no_pinned_locations_message).visibility = View.VISIBLE
                } else {
                    findViewById<TextView>(R.id.no_pinned_locations_message).visibility = View.GONE
                }
            }
        }
    }



    fun removeValuesViaIteration(listWithNullsAndEmpty: MutableList<String>): List<String> {
        val iterator = listWithNullsAndEmpty.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (element == null || element.isEmpty()) {
                iterator.remove()
            }
        }
        return listWithNullsAndEmpty as List<String>
    }



    private fun removePin(locationName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val userId = getCurrentUserUID()
            val userRepository = UserRepository(this@MapsActivity)
            userRepository.deleteUserLocation(userId, locationName)
            withContext(Dispatchers.Main) {
                updateDropdownList()
            }
        }
    }



    private fun onDeletePinClick(marker: Marker) {
        lifecycleScope.launch(Dispatchers.Main) {
            val title = marker.title ?: ""

            withContext(Dispatchers.IO) {
                val db = appDatabase.getInstance(applicationContext)
                val userDataDao = db.userDataDao()
                val userDataInstance = userDataDao.getUserDataByUID(getCurrentUserUID())

                if (userDataInstance != null) {
                    val newPinnedLocations = userDataInstance.getPinnedLocations().toMutableList().filterNotNull().toMutableList()
                    newPinnedLocations.remove(title)
                    userDataInstance.setPinnedLocations(newPinnedLocations)
                    userDataDao.update(userDataInstance)

                    withContext(Dispatchers.Main) {
                        updateDropdownList()
                        Toast.makeText(this@MapsActivity, "Pin deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.groupId == R.id.action_pinned_locations) {
            moveToPinnedLocation(item.title.toString())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    private fun getCurrentUserUID(): Int {
        val userSessionManager = UserSessionManager(this)
        val uid = userSessionManager.getUserUID()
        Log.d("MapsActivity", "UID from session: $uid")
        return uid
    }



}



