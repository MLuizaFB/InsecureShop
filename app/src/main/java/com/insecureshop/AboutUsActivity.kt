package com.insecureshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.databinding.ActivityAboutUsBinding
import com.insecureshop.util.Prefs


class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    lateinit var receiver: CustomReceiver

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendDataToBroadcast.setOnClickListener{
            performSendData()
        }

        receiver = CustomReceiver()
        val filter = IntentFilter("com.insecureshop.CUSTOM_INTENT")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun performSendData(){
        val prefs = Prefs.getInstance(applicationContext)
        val userName = prefs.username
        val password = prefs.password

        val intent = Intent("com.insecureshop.action.BROADCAST")
        intent.putExtra("username", userName)
        intent.putExtra("password", password)
        sendBroadcast(intent)

        binding.textView.text = "InsecureShop is an intentionally designed vulnerable android app built in Kotlin."
    }


}