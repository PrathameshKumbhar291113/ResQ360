package com.resq360.features.utils

import android.text.InputFilter

object Constants {
}

object InputFilters {
    val emailFilter = InputFilter { source, _, _, _, _, _ ->
        source?.filter { char -> char.isLetterOrDigit() || char =='@' || char == '.' } }
}