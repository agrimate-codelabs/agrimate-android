package com.codelabs.agrimate.ui.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AGWebView(url: String) {
    AndroidView(factory = { context ->
        return@AndroidView WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
        }
    },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}