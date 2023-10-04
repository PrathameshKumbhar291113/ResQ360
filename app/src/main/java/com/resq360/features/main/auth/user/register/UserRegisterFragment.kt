package com.resq360.features.main.auth.user.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentUserRegisterBinding
import com.resq360.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentUserRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    private fun setUpUI() {
        enableSubmitButton()
        binding.btnRegister.setOnClickListener {
        //api call for registeration
            findNavController().navigate(R.id.homeFragment)
        }

        binding.ivSkipImg.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun enableSubmitButton() {
        binding.btnRegister.alpha = 1f
    }

    private fun disableSubmitButton() {
        binding.btnRegister.alpha = .5f
    }
}