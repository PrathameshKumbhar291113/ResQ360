package com.resq360.features.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentHomeBinding
import com.resq360.features.utils.Agency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var agencyAdapter: AgencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.lightish_teal_green)
        val agencies = listOf(Agency("Mumbai Fire Department", "0000-1230456", "3 kms away"),
            Agency("Police Department", "0000-1530496", "5 kms away"),
            Agency("Mumbai Medical Department", "0000-1478956", "10 kms away"),
            Agency("National Disaster Management Authority", "0000-1220466", "8 kms away"),
            Agency("District Disaster Management Authority", "0000-1260446", "4 kms away"),
            Agency("Mumbai Police Department", "0000-1335656", "80 kms away"),
            Agency("Ambulance Department", "0000-1450786", "90 kms away"),
            Agency("Delhi Fire Department", "0000-1769056", "30 kms away")
        )
        agencyAdapter = AgencyAdapter(requireContext(), agencies)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {

        binding.agencyList.adapter = agencyAdapter
    }
}