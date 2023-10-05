package com.resq360.features.main.auth.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.resq360.R
import com.resq360.databinding.FragmentUserLoginBinding
import com.resq360.features.base.BaseFragment
import com.resq360.features.main.auth.user.otp_verification.UserOtpVerificationFragment
import com.resq360.features.utils.BundleConstants
import com.resq360.features.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class UserLoginFragment : BaseFragment() {
    private lateinit var binding: FragmentUserLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var _verificationId : String = ""

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
        auth = FirebaseAuth.getInstance()
        binding.userMobileNumberEditText.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (it.length < 10) {
                    disableSubmitButton()
                } else if (it.length == 10) {
                    enableSubmitButton()
                    requireActivity().hideKeyboard()
                }
            }
        }

        binding.btnGetOtp.setOnClickListener {
            requireActivity().hideKeyboard()
            binding.userMobileNumberEditText.text?.let {
                if (it.isNullOrEmpty() || it.isNullOrBlank()) {
                    errorToast("Mobile number cannot be empty.")
                    disableSubmitButton()
                } else if (it.length < 10) {
                    errorToast("Mobile number cannot be less than 10 digits.")
                    disableSubmitButton()
                } else if (checkForInternet(requireContext())) {
                    binding.mainContainer.isVisible = false
                    binding.progressBarContainer.isVisible = true
                    sendOtp("+91${it.toString().trim()}")
                } else {
                    errorToast("Kindly connect to internet to proceed")
                }
            }

        }

        callBackFunctionForPhoneAuth()
    }

    private fun callBackFunctionForPhoneAuth() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                findNavController().navigate(R.id.homeFragment)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    errorToast("Invalid phone number.")
                } else if (e is FirebaseTooManyRequestsException) {
                    errorToast("Quota exceeded.")
                }
                binding.mainContainer.isVisible = true
                binding.progressBarContainer.isVisible = false
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                successToast("OTP Send Successfully!!!")
                _verificationId = verificationId
                resendToken = token

                val bundle = bundleOf(
                    UserOtpVerificationFragment.USER_VERIFICATION_ID to _verificationId
                )
               findNavController().navigate(R.id.userOtpVerificationFragment,bundle)
            }
        }
    }

    private fun sendOtp(mobileNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobileNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun enableSubmitButton() {
        binding.btnGetOtp.alpha = 1f
    }

    private fun disableSubmitButton() {
        binding.btnGetOtp.alpha = .5f
    }
}