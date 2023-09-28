package com.resq360.features.main.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentSelectUserAgencyBinding
import com.resq360.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectUserAgencyFragment : BaseFragment() {
    private lateinit var binding: FragmentSelectUserAgencyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSelectUserAgencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.cardAgency.setOnClickListener {
            enableNextButton()
            binding.cardAgency.isChecked = true
            binding.cardUser.isChecked = false
        }
        binding.cardUser.setOnClickListener {
            enableNextButton()

            binding.cardAgency.isChecked = false
            binding.cardUser.isChecked = true
        }

        binding.nextBtn.setOnClickListener {
            enableDisableNextButton()
        }
    }

    private fun enableDisableNextButton() {
        if (binding.cardAgency.isChecked || binding.cardUser.isChecked){
            enableNextButton()
            navigateToSelectedFragment()
        }else{
            warningToast("Kindly Select One Role!!!")
            disableNextButton()
        }
    }

    private fun navigateToSelectedFragment() {
        if (binding.cardUser.isChecked){
            findNavController().navigate(R.id.userLoginFragment)
        }else if (binding.cardAgency.isChecked){
            findNavController().navigate(R.id.agencyLoginFragment)
        }
    }

    private fun disableNextButton() {
        binding.nextBtn.setBackgroundDrawable((ContextCompat.getDrawable(requireContext(), R.drawable.shape_layer_button_disable)))
    }

    private fun enableNextButton() {
        binding.nextBtn.setBackgroundDrawable((ContextCompat.getDrawable(requireContext(), R.drawable.shape_layer_button)))
    }

}