package com.codelabs.agrimate.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {

    @Test
    fun convertLongToTime() {
        val converted = DateUtils.convertLongToTime(1707264000000)
        assertEquals("07/02/2024", converted)
    }

    @Test
    fun convertDateToLong() {
        val converted = DateUtils.convertDateToLong("07/02/2024")
        assertEquals(1707264000000, converted)
    }

    @Test
    fun formatDate() {
        val converted = DateUtils.formatDate(
            "07/02/2024",
            currentFormat = "dd/MM/yyyy",
            targetFormat = "EEEE d MMMM yyyy"
        )
        assertEquals("Rabu 7 Februari 2024", converted)
    }

    @Test
    fun convertDateFormat() {
        val converted = DateUtils.convertDateFormat("07/02/2024")
        assertEquals("2024/02/07", converted)
    }
}