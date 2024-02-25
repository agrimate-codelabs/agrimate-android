package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.codelabs.agrimate.R
import com.codelabs.agrimate.utils.PermissionTextProvider

@Composable
fun AGPermissionDialog(
    modifier: Modifier = Modifier,
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onGrantClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = modifier.sizeIn(minWidth = 280.dp, maxWidth = 560.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.permission_required),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = permissionTextProvider.getDescription(isPermanentlyDeclined),
                    fontSize = 16.sp
                )
                Divider(modifier = Modifier.height(1.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = {
                        if (isPermanentlyDeclined) {
                            onGrantClick()
                        } else {
                            onConfirmClick()
                        }
                    }) {
                        Text(
                            text = if (isPermanentlyDeclined) {
                                stringResource(R.string.grant_access)
                            } else {
                                stringResource(R.string.ok)
                            },
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}