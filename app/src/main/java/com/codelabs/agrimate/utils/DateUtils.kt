package com.codelabs.agrimate.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    fun currentDate(format: String = "yyyy/MM/dd", locale: Locale = Locale("id", "ID")): String {
        val date = Date()
        val format = SimpleDateFormat(format, locale)
        return format.format(date)
    }

    fun convertLongToTime(
        time: Long,
        locale: Locale = Locale.getDefault(),
        formatPattern: String = "dd/MM/yyyy",
    ): String {
        val date = Date(time)
        val format = SimpleDateFormat(formatPattern, locale)
        return format.format(date)
    }

    fun convertDateToLong(
        date: String,
        locale: Locale = Locale.getDefault(),
        formatPattern: String = "dd/MM/yyyy",
        desiredTime: String = "07:00:00"
    ): Long {
        val dateFormat = SimpleDateFormat(formatPattern, locale)

        val parsedDate = dateFormat.parse(date)

        val calendar = Calendar.getInstance()
        calendar.time = parsedDate ?: Date()
        val timeComponents = desiredTime.split(":").map { it.toInt() }
        calendar.set(Calendar.HOUR_OF_DAY, timeComponents[0])
        calendar.set(Calendar.MINUTE, timeComponents[1])
        calendar.set(Calendar.SECOND, timeComponents[2])

        return calendar.timeInMillis
    }

    fun formatDate(
        currentDate: String,
        currentFormat: String = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'",
        targetFormat: String = "d MMMM yyyy",
        timezone: String = "GMT",
        locale: Locale = Locale("id", "ID")
    ): String? {
        val currentDf: DateFormat = SimpleDateFormat(currentFormat, locale)
        currentDf.timeZone = TimeZone.getTimeZone(timezone)
        val targetDf: DateFormat = SimpleDateFormat(targetFormat, locale)
        var targetDate: String? = null
        try {
            val date = currentDf.parse(currentDate)
            if (date != null) {
                targetDate = targetDf.format(date)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return targetDate
    }

    fun convertDateFormat(
        inputDate: String,
        inputPattern: String = "dd/MM/yyyy",
        outputPattern: String = "yyyy/MM/dd",
        errorOutput: String = "",
        locale: Locale = Locale("id", "ID")
    ): String {
        val inputFormat = SimpleDateFormat(inputPattern, locale)
        val outputFormat = SimpleDateFormat(outputPattern, locale)

        return try {
            val date = inputFormat.parse(inputDate)
            if (date != null) {
                outputFormat.format(date)
            } else {
                errorOutput
            }
        } catch (e: Exception) {
            errorOutput
        }
    }

    fun currentTime(): Long {
        return System.currentTimeMillis()
    }
}