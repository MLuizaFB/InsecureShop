package com.insecureshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.insecureshop.broadcast.ProductDetailBroadCast
import com.insecureshop.databinding.ActivityProductListBinding
import com.insecureshop.util.Prefs
import com.insecureshop.util.Util
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    private val productDetailBroadCast = ProductDetailBroadCast()

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        lifecycleScope.launch {
            val username = Prefs.getUsername(applicationContext)
            if (TextUtils.isEmpty(username)) {
                val intent = Intent(this@ProductListActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return@launch
            }
        }

        val intentFilter = IntentFilter("com.insecureshop.action.PRODUCT_DETAIL")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(productDetailBroadCast, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(productDetailBroadCast, intentFilter)
        }

        val productAdapter = ProductAdapter()

        binding.recyclerview.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.recyclerview.adapter = productAdapter

        productAdapter.productList = Util.getProductsPrefs(this)
        productAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.logout) {
            lifecycleScope.launch {
                Prefs.clearAll(applicationContext)
                val intent = Intent(this@ProductListActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        if (item.itemId == R.id.cart) {
            val intent = Intent(this, CartListActivity::class.java)
            startActivity(intent)
        }
        if(item.itemId == R.id.about){
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        return true
    }

}
