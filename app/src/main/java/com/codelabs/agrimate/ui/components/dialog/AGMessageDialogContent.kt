package com.codelabs.agrimate.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGButtonOutlined
import com.codelabs.agrimate.ui.theme.Red1

@Composable
fun AGMessageDialogContent(modifier: Modifier, message: String) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier
                .size(120.dp),
            imageVector = Icons.Filled.Error,
            contentDescription = "Peringatan",
            tint = Red1
        )
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
        Text(
            text = message,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AGMessageDialogContentExample() {
    Surface(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            AGMessageDialogContent(
                modifier = Modifier.padding(vertical = 24.dp),
                message = "Apakah Anda yakin ingin menyatakan gagal panen?"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AGButtonOutlined(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 15.5.dp)
                ) {
                    Text(text = "Kembali", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
                AGButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 15.5.dp),
                ) {
                    Text(
                        text = "Saya yakin", fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                    )
                }
            }
        }
    }
}