package com.codelabs.agrimate.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

object FormatUtils {
    fun formatNumber(string: String, locale: Locale = Locale("id", "ID")): String {
        val result = try {
            val formatter = NumberFormat.getNumberInstance(
                locale
            )
            val number = string.toLong()
            val formattedString = formatter.format(number)
            formattedString
        } catch (e: Exception) {
            string
        }
        return result
    }

    fun formatDecimal(number: Double, locale: Locale = Locale.ENGLISH): String {
        val df = DecimalFormat("#.##", DecimalFormatSymbols(locale))
        return df.format(number)
    }
}