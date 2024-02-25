package com.codelabs.agrimate.utils

import androidx.compose.ui.graphics.Color

object UiUtils {
    fun getStatusColor(status: String) = when (status.lowercase()) {
        "belum panen" -> Color(0xFFF7C703)
        "panen" -> Color(0xFF1A9D8C)
        else -> Color(0xFFFF5E5E)
    }
}
