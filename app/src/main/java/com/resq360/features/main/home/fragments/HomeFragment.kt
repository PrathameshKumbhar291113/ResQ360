package com.resq360.features.main.home.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.resq360.R
import com.resq360.databinding.BottomSheetAgencyContactBinding
import com.resq360.databinding.FragmentHomeBinding
import com.resq360.features.base.BaseFragment
import com.resq360.features.main.home.adapters.AgencyAdapter
import com.resq360.features.main.home.models.Agency
import com.resq360.utils.RoundedBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var agencyAdapter: AgencyAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val REQUEST_PHONE_CALL = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor =
            requireActivity().getColor(R.color.lightish_teal_green)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
//        auth.signOut()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        checkPermission()

        val agencies = listOf(
            Agency(
                "Mumbai Fire Department",
                "+91 9082379158",
                "3 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "District Disaster Management Authority",
                "+91 9082379158",
                "4 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "Police Department",
                "+91 9082379158",
                "5 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "Mumbai Medical Department",
                "+91 9082379158",
                "10 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "National Disaster Management Authority",
                "+91 9082379158",
                "8 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "Mumbai Police Department",
                "+91 9082379158",
                "80 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "Ambulance Department",
                "+91 9082379158",
                "90 kms away",
                "Mumbai, Maharashtra, India"
            ),
            Agency(
                "Navi Mumbai Fire Department",
                "+91 9082379158",
                "30 kms away",
                "Mumbai, Maharashtra, India"
            )
        )
        agencyAdapter = AgencyAdapter(requireContext(), agencies, this::onItemClick)

        binding.agencyList.adapter = agencyAdapter

        setUpNavDrawer()


    }

    private fun setUpNavDrawer() {
        drawerLayout = binding.navDrawerLayout
        navigationView = binding.navDrawerNavigationView

        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        actionBarDrawerToggle.syncState()

        drawerLayout.addDrawerListener(actionBarDrawerToggle)


        binding.topTitleBar.titleBarMenuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setCheckedItem(R.id.home)
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.home) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (it.itemId == R.id.profile) {
                drawerLayout.closeDrawer(GravityCompat.START)
                infoToast("Soon Comming...")

            } else if (it.itemId == R.id.exploreMore) {
                drawerLayout.closeDrawer(GravityCompat.START)
                infoToast("Soon Comming...")

            }
            return@setNavigationItemSelectedListener true
        }


    }

    private fun onItemClick(agency: Agency) {

        val bottomSheetAgencyContactDialog = RoundedBottomSheetDialog(requireContext())
        val bottomSheetAgencyContactBinding =
            BottomSheetAgencyContactBinding.inflate(layoutInflater)

        bottomSheetAgencyContactDialog.apply {
            setContentView(
                bottomSheetAgencyContactBinding.root
            )
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            bottomSheetAgencyContactBinding.valueAgencyName.text = agency.agencyName.toString()
            bottomSheetAgencyContactBinding.valueAgencyTollFreeNo.text =
                agency.agencyNumber.toString()
            bottomSheetAgencyContactBinding.valueAgencyAddress.text = agency.address.toString()

            bottomSheetAgencyContactBinding.sliderButton.rightSwipeListener = {
                successToast("Calling ${agency.agencyName} on ${agency.agencyNumber}")
                bottomSheetAgencyContactDialog.dismiss()
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel: ${agency.agencyNumber}")
                startActivity(callIntent)
            }

        }.show()

        bottomSheetAgencyContactBinding.imgBottomSheetClose.setOnClickListener {
            bottomSheetAgencyContactDialog.dismiss()
        }

    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                101
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call.
                successToast("Successfully Permission Granted.")

            } else {
                // Permission denied. You can handle this case as needed.
                warningToast("Kindly Provide Permission.")
            }
        }
    }

}