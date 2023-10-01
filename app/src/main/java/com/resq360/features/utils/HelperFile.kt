package com.resq360.features.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this.currentFocus?.rootView?.windowToken, 0)
}

