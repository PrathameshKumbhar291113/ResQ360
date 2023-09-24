package com.resq360.features.main.auth.agency.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resq360.databinding.FragmentAgencyForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgencyForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentAgencyForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentAgencyForgotPasswordBinding.inflate(inflater,container, false)
        return binding.root
    }
}