package com.insecureshop

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.databinding.ActivityWebviewBinding
import com.insecureshop.util.CustomWebViewClient
import com.insecureshop.util.Prefs

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.webview)

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.allowUniversalAccessFromFileURLs = true
        binding.webview.settings.userAgentString = USER_AGENT
        binding.webview.webViewClient = CustomWebViewClient()

        val uri : Uri? = intent.data
        uri?.let {
            var data: String? = null
            if (uri.path.equals("/web")) {
                data = intent.data?.getQueryParameter("url")
            } else if (uri.path.equals("/webview")) {
                if (intent.data!!.getQueryParameter("url")!!.endsWith("insecureshopapp.com")) {
                    data = intent.data?.getQueryParameter("url")
                }
            }

            if (data == null) {
                finish()
                return@let
            }
            binding.webview.loadUrl(data)
            Prefs.getInstance(this).data = data
        }

    }


}
