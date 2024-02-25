package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.GreyScale500

@Composable
fun AGMessageDialog(
    onDismissRequest: () -> Unit,
    icon: Painter,
    title: String,
    description: String
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            color = Color.White,
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(55.dp),
                    painter = icon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    color = Color(0XFF212121),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(bottom = 5.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = GreyScale500
                )
            }
        }
    }
}

@Composable
fun AGMessageDialogSuccess(onDismissRequest: () -> Unit, title: String, description: String) {
    AGMessageDialog(
        onDismissRequest = onDismissRequest,
        icon = painterResource(id = R.drawable.ag_success_icon),
        title = title,
        description = description
    )
}