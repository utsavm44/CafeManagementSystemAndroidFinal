package com.utsav.cafemanagementsystem.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.utsav.cafemanagementsystem.R

class MapsFragment : Fragment() {



        private val callback = OnMapReadyCallback { googleMap ->
            val Office = LatLng(27.7061949, 85.3300394)
            googleMap.addMarker(MarkerOptions().position(Office).title(" Restaurant Location "))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(Office))
        }

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val view: View = inflater.inflate(R.layout.fragment_maps, container, false)
            return view
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
    }