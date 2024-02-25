package com.codelabs.agrimate.utils

object TimeUtils {
    private val TIME_STR_PATTERN = Regex("\\d{2}:\\d{2}\$")
    fun parseHour(string: String): Int {
        return if (string.isNotEmpty()) {
            if (string.matches(TIME_STR_PATTERN)) {
                string.split(':')[0].toInt()
            } else {
                0
            }
        } else {
            0
        }
    }

    fun parseMinute(string: String): Int {
        return if (string.isNotEmpty()) {
            if (string.matches(TIME_STR_PATTERN)) {
                string.split(':')[1].toInt()
            } else {
                0
            }
        } else {
            0
        }
    }

    fun formatTime(hour: Int, minute: Int): String {
        val formattedHour = if (hour < 10) "0$hour" else hour
        val formattedMinute = if (minute < 10) "0$minute" else minute
        return "$formattedHour:$formattedMinute"
    }

    fun getTimerLabel(value: Int): String {
        return "${padding(value / 60)} : ${padding(value % 60)}"
    }

    fun padding(value: Int) = if (value < 10) ("0$value") else "" + value
}