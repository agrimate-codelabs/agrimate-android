package com.codelabs.agrimate.ui.components.visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.codelabs.agrimate.utils.FormatUtils

class NumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = FormatUtils.formatNumber(text.text)
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return transformedText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return text.length
            }

        }

        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}