package com.demoapp.passwordmanager.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun convertDateIntoString(date: Date): String {
        val builder = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return builder.format(date)
    }

}