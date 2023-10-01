package com.resq360.features.main.auth.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentUserLoginBinding
import com.resq360.features.base.BaseFragment
import com.resq360.features.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserLoginFragment : BaseFragment() {
    private lateinit var binding: FragmentUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUI()
    }

    private fun setUpUI() {
        binding.userMobileNumberEditText.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (it.length <10){
                    disableSubmitButton()
                }else if (it.length == 10) {
                    enableSubmitButton()
                    requireActivity().hideKeyboard()
                }
            }
        }

        binding.btnGetOtp.setOnClickListener {
            requireActivity().hideKeyboard()
            binding.userMobileNumberEditText.text?.let {
                if (it.isNullOrEmpty() || it.isNullOrBlank()){
                    errorToast("Mobile number cannot be empty.")
                    disableSubmitButton()
                }else if(it.length < 10){
                    errorToast("Mobile number cannot be less than 10 digits.")
                    disableSubmitButton()
                }else if (checkForInternet(requireContext())) {
                    findNavController().navigate(R.id.userOtpVerificationFragment)
                } else {
                    errorToast("Kindly connect to internet to proceed")
                }
            }

        }
    }

    private fun enableSubmitButton() {
        binding.btnGetOtp.alpha = 1f
    }

    private fun disableSubmitButton() {
        binding.btnGetOtp.alpha = .5f
    }
}