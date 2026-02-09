package com.insecureshop

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.databinding.ActivityLoginBinding
import com.insecureshop.util.Prefs
import com.insecureshop.util.Util

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 100)

        binding.btnLogin.setOnClickListener{
            performLogin()
        }
    }

    private fun performLogin() {
        val username = binding.edtUserName.text.toString()
        val password = binding.edtPassword.text.toString()

        Log.d("userName", username)
        Log.d("password", password)


        val auth = Util.verifyUserNamePassword(username, password)
        if (auth) {
            Prefs.getInstance(applicationContext).username = username
            Prefs.getInstance(applicationContext).password = password
            Util.saveProductList(this)
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        } else {
            for (info in packageManager.getInstalledPackages(0)) {
                val packageName = info.packageName
                if (packageName.startsWith("com.insecureshopapp")) {
                    try {
                        val packageContext = createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE or Context.CONTEXT_IGNORE_SECURITY)
                        val value: Any = packageContext.classLoader
                            .loadClass("com.insecureshopapp.MainInterface")
                            .getMethod("getInstance", Context::class.java)
                            .invoke(null, this)
                        Log.d("object_value", value.toString())
                    } catch (e: Exception) {
                        throw RuntimeException(e)
                    }
                }
            }

            Toast.makeText(applicationContext, "Invalid username and password", Toast.LENGTH_LONG)
                .show()
        }
    }
}
