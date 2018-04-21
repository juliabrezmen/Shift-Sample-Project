package com.juliadanylyk.shift.utils

import com.juliadanylyk.shift.Dependencies.DEPENDENCIES
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {
        private const val ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
        private const val DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm a"

        fun parseUtcDate(stringDate: String?): Long? {
            val format = SimpleDateFormat(ISO_8601_DATE_FORMAT, Locale.getDefault())
            return try {
                format.parse(stringDate).time
            } catch (e: ParseException) {
                DEPENDENCIES.logger.e("Exception during parsing string to date", e)
                null
            }
        }

        fun toUtcDate(time: Long): String = SimpleDateFormat(ISO_8601_DATE_FORMAT, Locale.getDefault()).format(Date(time))

        fun toDisplayableDate(time: Long): String {
            return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(Date(time))
        }
    }
}