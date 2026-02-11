package com.insecureshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.databinding.ActivityPrivateBinding
import com.insecureshop.util.Prefs


class PrivateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivateBinding

    val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = getString(R.string.webview)

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.userAgentString = USER_AGENT
        binding.webview.settings.allowUniversalAccessFromFileURLs = true

        var data = intent.getStringExtra("url")
        if (data == null) {
            data = "https://www.insecureshopapp.com"
        }

        binding.webview.loadUrl(data)
        Prefs.getInstance(this).data = data
    }
}
