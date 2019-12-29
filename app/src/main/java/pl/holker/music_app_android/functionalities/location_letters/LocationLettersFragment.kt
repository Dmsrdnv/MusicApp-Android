package pl.holker.music_app_android.functionalities.location_letters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.holker.music_app_android.R
import pl.holker.music_app_android.databinding.FragmentLocationLetterBinding
import pl.holker.music_app_android.di.Injectable
import pl.holker.music_app_android.di.ViewModelInjectionFactory
import javax.inject.Inject

class LocationLettersFragment @Inject constructor() : Fragment(), Injectable, OnMapReadyCallback {

    private val TAG = LocationLettersFragment::class.java.name

    private lateinit var _viewModel: LocationLettersVM
    private lateinit var _binding: FragmentLocationLetterBinding

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<LocationLettersVM>


    private lateinit var _map: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        _map = googleMap
        Log.i(TAG, "onMapReady method was invoked")
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        _map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        _map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_letter, container, false)
        _viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory).get(LocationLettersVM::class.java)
        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val mapFragment =
            fragmentManager?.findFragmentById(R.id.fragment_location_letters_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
}