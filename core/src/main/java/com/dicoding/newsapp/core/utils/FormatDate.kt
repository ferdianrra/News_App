package com.dicoding.newsapp.core.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object FormatDate {
    fun formatDate(isoDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss")
        val zonedDateTime = ZonedDateTime.parse(isoDate)
        return zonedDateTime.format(formatter)
    }
}