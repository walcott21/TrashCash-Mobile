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

        val lisbonCoordinates = GeoPoint(38.7223, -9.1393)
        mapView.controller.setZoom(12.0)
        mapView.controller.setCenter(lisbonCoordinates)

        addCollectionPoints()
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
            marker.title = point.name
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
    }

    data class CollectionPoint(val name: String, val latitude: Double, val longitude: Double)
}
