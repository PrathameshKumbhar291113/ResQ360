package com.resq360.features.utils

import android.text.InputFilter

object Constants {
    const val API_BASE_URL=""
    const val GET_AGENCIES="/getAgencies"
}

object InputFilters {
    val emailFilter = InputFilter { source, _, _, _, _, _ ->
        source?.filter { char -> char.isLetterOrDigit() || char =='@' || char == '.' }
    }
}