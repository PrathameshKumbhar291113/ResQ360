package com.resq360.features.main.auth.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resq360.R
import com.resq360.databinding.FragmentUserLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserLoginFragment : Fragment() {
    private lateinit var binding: FragmentUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
}