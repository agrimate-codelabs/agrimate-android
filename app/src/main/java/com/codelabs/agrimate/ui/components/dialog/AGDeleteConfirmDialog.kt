package com.codelabs.agrimate.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.codelabs.agrimate.ui.components.AGButton
import com.codelabs.agrimate.ui.components.AGButtonOutlined

@Composable
fun AGDeleteConfirmDialog(onDismissRequest: () -> Unit, onDeleteClick: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                AGMessageDialogContent(
                    modifier = Modifier.padding(vertical = 24.dp),
                    message = "Apakah Anda yakin ingin menghapus lahan ini?"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AGButtonOutlined(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 15.5.dp)
                    ) {
                        Text(text = "Batal", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    }
                    AGButton(
                        onClick = onDeleteClick,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 15.5.dp),
                    ) {
                        Text(
                            text = "Hapus", fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
