package com.example.drawernav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class homeFragment : Fragment() {
    private lateinit var mapView: MapView
    private lateinit var fabSearch: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        Configuration.getInstance().userAgentValue = requireContext().packageName

        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        val lisbonCoordinates = GeoPoint(38.7223, -9.1393)
        mapView.controller.setZoom(12.0)
        mapView.controller.setCenter(lisbonCoordinates)

        addCollectionPoints()

        fabSearch = view.findViewById(R.id.fab_search)
        fabSearch.setOnClickListener {
            // Handle search button click
            // Example: Toast.makeText(requireContext(), "Search clicked", Toast.LENGTH_SHORT).show()
        }

        //bottomAppBar = view.findViewById(R.id.bottomAppBar)
        // Set up the bottom app bar

        return view
    }

    private fun addCollectionPoints() {
        // Add your collection points here
        val collectionPoints = listOf(
            CollectionPoint("Collection Point 1", 38.7223, -9.1393),
            CollectionPoint("Collection Point 2", 38.7241, -9.1374),
            CollectionPoint("Collection Point 3", 38.7256, -9.1423),
            CollectionPoint("Collection Point 4", 38.7198, -9.1376),
            CollectionPoint("Collection Point 5", 38.7209, -9.1352)
        )

        for (point in collectionPoints) {
            val marker = Marker(mapView)
            marker.position = GeoPoint(point.latitude, point.longitude)
            marker.setOnMarkerClickListener { marker, mapView ->
                // Print a message to the console
                println("Marker clicked: ${marker.title}")

                // Return false to indicate that the event is not consumed
                false
            }

            marker.title = point.name
            mapView.overlays.add(marker)
        }
    }

    data class CollectionPoint(val name: String, val latitude: Double, val longitude: Double)
}
