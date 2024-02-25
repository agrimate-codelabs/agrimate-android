package com.codelabs.agrimate.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatUtilsTest {

    @Test
    fun formatNumber() {
        assertEquals("1.234", FormatUtils.formatNumber("1234"))
        assertEquals("1.234.456", FormatUtils.formatNumber("1234456"))
    }

    @Test
    fun formatDecimal() {
        assertEquals("0.12", FormatUtils.formatDecimal(0.1197219591))
        assertEquals("0.1", FormatUtils.formatDecimal(0.1))
    }
}