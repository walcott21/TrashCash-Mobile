package com.example.trashcash_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity() {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        Configuration.getInstance().userAgentValue = packageName
        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        addCollectionPoints()
    }

    private fun addCollectionPoints() {
        // Add your collection points here
        val collectionPoints = listOf(
            CollectionPoint("Collection Point 1", 37.7749, -122.4194),
            CollectionPoint("Collection Point 2", 37.7739, -122.4325),
            CollectionPoint("Collection Point 3", 37.7706, -122.4357)
        )

        for (point in collectionPoints) {
            val marker = Marker(mapView)
            marker.position = GeoPoint(point.latitude, point.longitude)
            marker.title = point.name
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
    }

    data class CollectionPoint(val name: String, val latitude: Double, val longitude: Double)
}
