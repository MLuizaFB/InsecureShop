package com.insecureshop

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.databinding.ActivityImplicitIntentForNonExportedBinding


class SendingDataViaActionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImplicitIntentForNonExportedBinding

    val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplicitIntentForNonExportedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.logout.setOnClickListener {
            performSendData()
        }
    }

    private fun performSendData() {
        val intent = Intent("com.insecureshop.action.WEBVIEW")
        intent.putExtra("url", "https://www.insecureshop.com/")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {

        }
    }

}
