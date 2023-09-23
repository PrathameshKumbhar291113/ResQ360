package com.resq360.features.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.resq360.R
import dagger.hilt.android.AndroidEntryPoint
import splitties.views.inflate

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    //Custom function to show the custom toast
    fun successToast(message: String) {

        val successToastLayout: View = layoutInflater.inflate(R.layout.toast_success)

        Toast(applicationContext).apply {
            duration = Toast.LENGTH_SHORT
            successToastLayout.findViewById<TextView>(R.id.successToastText).text = message
            setGravity(Gravity.BOTTOM, 0, 200)
            view = successToastLayout
            show()
        }
    }

    fun errorToast(message: String) {

        val errorToastLayout: View = layoutInflater.inflate(R.layout.toast_error)

        Toast(applicationContext).apply {
            duration = Toast.LENGTH_SHORT
            errorToastLayout.findViewById<TextView>(R.id.errorToastText).text = message
            setGravity(Gravity.BOTTOM, 0, 200)
            view = errorToastLayout
            show()
        }
    }

    fun infoToast(message: String) {

        val infoToastLayout: View = layoutInflater.inflate(R.layout.toast_information)

        Toast(applicationContext).apply {
            duration = Toast.LENGTH_SHORT
            infoToastLayout.findViewById<TextView>(R.id.infoToastText).text = message
            setGravity(Gravity.BOTTOM, 0, 200)
            view = infoToastLayout
            show()
        }
    }

    fun warningToast(message: String) {

        val warningToastLayout: View = layoutInflater.inflate(R.layout.toast_warning)

        Toast(applicationContext).apply {
            duration = Toast.LENGTH_SHORT
            warningToastLayout.findViewById<TextView>(R.id.warningToastText).text = message
            setGravity(Gravity.BOTTOM, 0, 200)
            view = warningToastLayout
            show()
        }
    }

    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}