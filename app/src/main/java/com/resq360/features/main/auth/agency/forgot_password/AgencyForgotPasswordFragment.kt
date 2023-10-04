package com.resq360.features.main.auth.agency.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.resq360.databinding.FragmentAgencyForgotPasswordBinding
import com.resq360.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgencyForgotPasswordFragment : BaseFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
    }

    private fun setUpUi() {
        binding.btnSubmit.setOnClickListener {
            if (binding.userRestorePasswordEditText.text.isNullOrBlank() || binding.userRestorePasswordEditText.text.isNullOrEmpty()){
                //TODO make api call
            }else{
                errorToast("Email cannot be empty.")
            }
        }
    }
}