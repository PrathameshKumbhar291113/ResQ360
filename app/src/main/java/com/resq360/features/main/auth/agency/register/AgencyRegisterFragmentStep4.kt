package com.resq360.features.main.auth.agency.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.resq360.R
import com.resq360.databinding.FragmentAgencyRegisterStep4Binding

class AgencyRegisterFragmentStep4 : Fragment() {
    private lateinit var binding: FragmentAgencyRegisterStep4Binding
    private val PICK_DOCUMENT_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentAgencyRegisterStep4Binding.inflate(inflater, container, false)
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

        binding.submitButton.setOnClickListener {
            findNavController().navigate(R.id.submitFragment)
        }

        binding.uploadDoc.setOnClickListener {
            onUploadButtonClick()
        }
    }

    fun onUploadButtonClick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*/*"
        intent.type = "application/pdf"
        startActivityForResult(intent, PICK_DOCUMENT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_DOCUMENT_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedDocumentUri = data.data
            // Handle the selected document here
            Log.e("Selected Doc URI", selectedDocumentUri.toString())
        }
    }
}