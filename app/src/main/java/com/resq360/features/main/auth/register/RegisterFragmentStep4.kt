package com.resq360.features.main.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resq360.databinding.FragmentRegisterStep4Binding

class RegisterFragmentStep4 : Fragment() {
    private lateinit var binding: FragmentRegisterStep4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentRegisterStep4Binding.inflate(inflater, container, false)
        return binding.root
    }
}