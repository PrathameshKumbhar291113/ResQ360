package com.resq360.features.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resq360.R
import com.resq360.databinding.FragmentHomeBinding
import com.resq360.features.utils.Agency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var agencies: ArrayList<Agency>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.lightish_teal_green)
        val agencyList = binding.agencyList
        agencies.add(Agency("Mumbai Fire Department", "0000-1230456", "3 kms away"))
        val agencyAdapter = AgencyAdapter(requireContext(), agencies)
        agencyList.adapter = agencyAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
}