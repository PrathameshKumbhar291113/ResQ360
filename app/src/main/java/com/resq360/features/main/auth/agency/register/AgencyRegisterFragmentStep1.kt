package com.resq360.features.main.auth.agency.register

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.resq360.R
import com.resq360.databinding.FragmentAgencyRegisterStep1Binding
import com.resq360.utils.isValidEmail
import com.resq360.utils.isValidPhoneNumber
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class AgencyRegisterFragmentStep1 : Fragment() {
    private lateinit var binding: FragmentAgencyRegisterStep1Binding
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        geocoder = Geocoder(requireContext(), Locale.getDefault())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgencyRegisterStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObserver()
    }

    private fun isLocationPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun requestLocation() {

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                Log.d("Location", location.toString())
                try {
                    val addresses: List<Address> =
                        geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
                    if (addresses.isNotEmpty()) {
                        val locationName = addresses[0].getAddressLine(0)
                        binding.agencyAddress.text = locationName
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }.addOnFailureListener { e ->
            Log.e("Error in Auto Detecting Location", "Please try again later")
        }
    }

    private fun setupObserver() {

    }
    private fun setupUi() {

        binding.nextButton.setOnClickListener {
            val agencyName = binding.agencyNameET.text.toString()
            val agencyOrg = binding.orgET.text.toString()
            val agencyID = binding.agencyIDET.text.toString()
            val agencyAddress = binding.agencyAddress.text.toString()

            if(agencyName.isNotEmpty() && agencyOrg.isNotEmpty() && agencyID.isNotEmpty() && validateEmail() && validatePhoneNumber() && agencyAddress.isNotEmpty())
                findNavController().navigate(R.id.agencyRegisterFragmentStep2)
            else
                Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show()
        }

        binding.agencyAddress.setOnClickListener {
            if (isLocationPermissionGranted()) {
                requestLocation()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.agencyEmailET.text.toString()
        return isValidEmail(email)
    }

    private fun validatePhoneNumber(): Boolean {
        val email = binding.agencyNumberET.text.toString()
        return isValidPhoneNumber(email)
    }
}