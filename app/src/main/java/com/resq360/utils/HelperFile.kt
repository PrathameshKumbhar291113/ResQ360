package com.resq360.utils

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this.currentFocus?.rootView?.windowToken, 0)
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    return Patterns.PHONE.matcher(phoneNumber).matches()
}

