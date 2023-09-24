package com.resq360.features.main.auth.agency.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resq360.databinding.FragmentAgencyRegisterStep4Binding

class AgencyRegisterFragmentStep4 : Fragment() {
    private lateinit var binding: FragmentAgencyRegisterStep4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentAgencyRegisterStep4Binding.inflate(inflater, container, false)
        return binding.root
    }
}