package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale500

@Composable
fun AGAlreadyHaveAccount(modifier: Modifier = Modifier, navigateToSignin: () -> Unit) {
    AGAuthNavAsk(
        modifier = modifier,
        question = "Sudah punya akun?",
        label = "Masuk Disini",
        navigate = navigateToSignin
    )
}

@Composable
fun AGNotHaveAccount(modifier: Modifier = Modifier, navigateToSignup: () -> Unit) {
    AGAuthNavAsk(
        modifier = modifier,
        question = "Belum punya akun?",
        label = "Daftar Disini",
        navigate = navigateToSignup
    )
}

@Composable
private fun AGAuthNavAsk(
    modifier: Modifier = Modifier,
    question: String,
    label: String,
    navigate: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
    ) {
        Text(
            text = question,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = GreyScale500
        )
        Text(
            modifier = Modifier.clickable { navigate() },
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Green100
        )
    }
}