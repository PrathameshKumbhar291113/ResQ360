package com.resq360.features.main.auth.user.otp_verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentUserOtpVerificationBinding
import com.resq360.features.base.BaseFragment
import com.resq360.features.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserOtpVerificationFragment : BaseFragment() {
    private lateinit var binding: FragmentUserOtpVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.userOtpEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length<6){
                    disableSubmitButton()
                }else if (it.length.equals(6)){
                    requireActivity().hideKeyboard()
                    enableSubmitButton()
                }
            }
        }
        binding.btnVerify.setOnClickListener {
            requireActivity().hideKeyboard()
            binding.userOtpEditText.text?.let {
                if (it.isNullOrBlank() || it.isNullOrEmpty()){
                    errorToast("OTP Cannot Be Empty!!!")
                    disableSubmitButton()
                }else if (it.length<6){
                    errorToast("OTP entered is less than 6 Digits!!!")
                    disableSubmitButton()
                }else if (checkForInternet(requireContext())){
                    findNavController().navigate(R.id.homeFragment)
                }else{
                    errorToast("Kindly connect to internet and try again later.")
                }
            }
        }
    }

    private fun enableSubmitButton() {
        binding.btnVerify.alpha = 1f
    }

    private fun disableSubmitButton() {
        binding.btnVerify.alpha = .5f
    }

}