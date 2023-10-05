package com.resq360.features.main.auth.agency.register

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentAgencyRegisterStep1Binding
import com.resq360.databinding.FragmentSubmitBinding

class SubmitFragment : Fragment() {

    private lateinit var binding: FragmentSubmitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToHome()
    }

    private fun navigateToHome() {
        Handler().postDelayed(Runnable {
            findNavController().navigate(R.id.homeFragment)
        }, 3000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}