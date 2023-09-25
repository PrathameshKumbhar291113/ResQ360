package com.resq360.features.main.auth.agency.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentAgencyLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgencyLoginFragment : Fragment() {
    private lateinit var binding: FragmentAgencyLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgencyLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObserver()
    }

    private fun setupObserver() {

    }

    private fun setupUi() {
        togglePasswordButton()

            binding.signupTextView.setOnClickListener {
                findNavController().navigate(R.id.agencyRegisterFragmentStep1)
            }
    }

    private fun togglePasswordButton() {
        with(binding) {
            passwordVisibleButton.setOnClickListener {
                if (passwordVisibleButton.isVisible) {
                    loginPasswordEditText.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    passwordVisibleButton.isVisible = false
                    passwordInvisibleButton.isVisible = true
                }

            }

            passwordInvisibleButton.setOnClickListener {
                if (passwordInvisibleButton.isVisible) {
                    loginPasswordEditText.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    passwordVisibleButton.isVisible = true
                    passwordInvisibleButton.isVisible = false
                }
            }
        }

    }
}