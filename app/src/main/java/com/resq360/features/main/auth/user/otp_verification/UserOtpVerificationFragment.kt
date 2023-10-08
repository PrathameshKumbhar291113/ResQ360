package com.resq360.features.main.auth.user.otp_verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.resq360.R
import com.resq360.databinding.FragmentUserOtpVerificationBinding
import com.resq360.features.base.BaseFragment
import com.resq360.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserOtpVerificationFragment : BaseFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentUserOtpVerificationBinding
    private lateinit var verificationId : String

    companion object{
        const val USER_VERIFICATION_ID = "verificationId"
    }

    private val _verificationId: String by lazy {
        arguments?.getString(USER_VERIFICATION_ID) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentUserOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        auth = FirebaseAuth.getInstance()
        binding.userOtpEditText.doOnTextChanged { text, start, before, count ->
            text?.let {
                if (it.length < 6) {
                    disableSubmitButton()
                } else if (it.length == 6) {
                    requireActivity().hideKeyboard()
                    enableSubmitButton()
                }
            }
        }
        binding.btnVerify.setOnClickListener {
            requireActivity().hideKeyboard()
            binding.userOtpEditText.text?.let {
                if (it.isNullOrBlank() || it.isNullOrEmpty()) {
                    errorToast("OTP Cannot Be Empty!!!")
                    disableSubmitButton()
                } else if (it.length < 6) {
                    errorToast("OTP entered is less than 6 Digits!!!")
                    disableSubmitButton()
                } else if (checkForInternet(requireContext())) {
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(_verificationId, it.toString().trim())
                    signInWithPhoneAuthCredential(credential)
                } else {
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

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                //api call to check if existing user or not
                findNavController().navigate(R.id.userRegisterFragment)
            } else {
                errorToast(task.exception?.message.toString())
            }
        }
    }


}