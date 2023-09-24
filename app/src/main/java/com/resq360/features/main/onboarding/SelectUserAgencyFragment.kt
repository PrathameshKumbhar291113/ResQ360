package com.resq360.features.main.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentSelectUserAgencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectUserAgencyFragment : Fragment() {
    private lateinit var binding: FragmentSelectUserAgencyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSelectUserAgencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.agency.setOnClickListener {
            findNavController().navigate(R.id.agencyLoginFragment)
        }
        binding.user.setOnClickListener {
            findNavController().navigate(R.id.userLoginFragment)
        }
    }

}