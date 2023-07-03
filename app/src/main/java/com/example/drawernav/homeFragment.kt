import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.drawernav.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class homeFragment : Fragment() {
    private lateinit var mapView: MapView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var collectionPoints: List<CollectionPoint>
    private lateinit var markers: List<Marker>

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

        collectionPoints = listOf(
            CollectionPoint("Collection Point 1", 38.7223, -9.1393),
            CollectionPoint("Collection Point 2", 38.7241, -9.1374),
            CollectionPoint("Collection Point 3", 38.7256, -9.1423),
            CollectionPoint("Collection Point 4", 38.7198, -9.1376),
            CollectionPoint("Collection Point 5", 38.7209, -9.1352)
        )

        markers = addCollectionPoints(collectionPoints)

        searchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query change
                filterMarkers(newText)
                return true
            }
        })

        return view
    }

    private fun addCollectionPoints(points: List<CollectionPoint>): List<Marker> {
        val markers = mutableListOf<Marker>()

        for (point in points) {
            val marker = Marker(mapView)
            marker.position = GeoPoint(point.latitude, point.longitude)
            marker.setOnMarkerClickListener { marker, mapView ->
                // Print a message to the console
                println("Marker clicked: ${marker.title}")

                // Show the pop-up dialog for the collection point
                showCollectionPointPopup(point)

                // Return true to consume the event
                true
            }

            marker.title = point.name
            mapView.overlays.add(marker)
            markers.add(marker)
        }

        return markers
    }

    private fun filterMarkers(query: String?) {
        var foundMarker: Marker? = null

        for (marker in markers) {
            if (query.isNullOrEmpty() || marker.title?.contains(query, ignoreCase = true) == true) {
                marker.isEnabled = true
                marker.setAlpha(1.0f) // Set marker opacity to fully visible

                // Check if the marker matches the search query
                if (marker.title?.equals(query, ignoreCase = true) == true) {
                    foundMarker = marker // Store the matching marker
                }
            } else {
                marker.isEnabled = false
                marker.setAlpha(0.0f) // Set marker opacity to fully transparent
            }
        }

        mapView.invalidate()

        // Move the map to the location of the found marker (if any) and zoom in
        if (foundMarker != null) {
            val point = GeoPoint(foundMarker.position.latitude, foundMarker.position.longitude)
            mapView.controller.setZoom(15.0) // Adjust the zoom level as desired (e.g., 15.0)
            mapView.controller.setCenter(point)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showCollectionPointPopup(collectionPoint: CollectionPoint) {
        // Create a custom layout for the pop-up dialog
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.popup_collection_point, null)

        // Find views in the custom layout
        val titleTextView = view.findViewById<TextView>(R.id.dialog_title)
        val trashTypesTextView = view.findViewById<TextView>(R.id.dialog_trash_types)
        val barcodeImageView = view.findViewById<ImageView>(R.id.dialog_barcode_image)

        // Set the collection point details in the views
        titleTextView.text = collectionPoint.name
        trashTypesTextView.text = "Accepted trash types:\n- Plastic\n- Metal\n- Paper"

        // Set the barcode image if available
        val barcodeDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.barcode)
        if (barcodeDrawable != null) {
            barcodeImageView.setImageDrawable(barcodeDrawable)
            barcodeImageView.visibility = View.VISIBLE
        } else {
            barcodeImageView.visibility = View.GONE
        }

        // Build the AlertDialog with the custom layout
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    data class CollectionPoint(val name: String, val latitude: Double, val longitude: Double)
}
