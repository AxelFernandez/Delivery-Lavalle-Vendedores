package com.axelfernandez.deliverylavallevendedores.ui.mapLocation

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.ui.orderDetail.OrderDetailArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsLocation : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val arguments = arguments?:return@OnMapReadyCallback
        val address = MapsLocationArgs.fromBundle(arguments).address
        val location = address.location?:return@OnMapReadyCallback
        val context= requireContext()
        val title = context.getString(R.string.detail_order_client_address, address.street, address.number, address.district, address.floor, address.reference).replace("null","")
        val latLong = location.split(",")
        val marker = LatLng(latLong[0].toDouble(), latLong[1].toDouble())
        googleMap.addMarker(MarkerOptions().position(marker).title(title))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(18F))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        mapFragment?.getMapAsync(callback)
    }
}