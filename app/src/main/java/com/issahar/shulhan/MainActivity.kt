package com.issahar.shulhan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.view.*


private const val SP_DARK_MODE = "SP_DARK_MODE"
private const val SP_OLD_COLORS = "SP_OLD_COLORS"
private const val SP_FONT_SIZE = "SP_FONT_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = applicationContext.getSharedPreferences("Shulahn_SP", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        val url = buildUrl()
        webView.loadUrl(url)
        webView.webViewClient = client
        webView.loadUrl(buildUrl());

    }

    private fun buildUrl(): String {
        val darkMode = "darkMode=${prefs.getBoolean(SP_DARK_MODE, false)}"
        val oldColors = "oldColors=${prefs.getBoolean(SP_OLD_COLORS, false)}"
        val fontSize = "fontSize=${prefs.getInt(SP_FONT_SIZE, 2)}"
        return "file:///android_asset/index.html?$darkMode&$fontSize&$oldColors"
    }

    private val client: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return if (request.url.scheme != "settings") {
                val intent = Intent(Intent.ACTION_VIEW, request.url)
                startActivity(intent)
                true
            }
            else {
                val darkMode = request.url.getQueryParameter("darkMode")
                val oldColors = request.url.getQueryParameter("oldColors")
                val fontSize = request.url.getQueryParameter("fontSize")
                if (darkMode != null)
                    prefs.edit().putBoolean(SP_DARK_MODE, darkMode.toBoolean()).apply()
                if (oldColors != null)
                    prefs.edit().putBoolean(SP_OLD_COLORS, oldColors.toBoolean()).apply()
                if (fontSize != null)
                    prefs.edit().putInt(SP_FONT_SIZE, fontSize.toInt()).apply()
                true
            }
        }
    }
}