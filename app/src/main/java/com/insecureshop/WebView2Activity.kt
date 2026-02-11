package com.insecureshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.databinding.ActivityWebviewBinding

class WebView2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.webview)

        val extraIntent = intent.getParcelableExtra<Intent>("extra_intent")
        if (extraIntent != null) {
            startActivity(extraIntent)
            finish()
            return
        }

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.allowUniversalAccessFromFileURLs = true
        binding.webview.settings.userAgentString = USER_AGENT
        if (!intent.dataString.isNullOrBlank()) {
            binding.webview.loadUrl(intent.dataString!!)
        } else if (!intent.data?.getQueryParameter("url").isNullOrBlank()) {
            binding.webview.loadUrl(intent.data?.getQueryParameter("url")!!)
        } else if(!intent.extras?.getString("url").isNullOrEmpty()){
            binding.webview.loadUrl(intent.extras?.getString("url")!!)
        }
    }
}
