package com.insecureshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.insecureshop.databinding.CartItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    var cartList : List<ProductDetail> = arrayListOf()

    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val prodDetail = cartList[position]
        Glide.with(holder.binding.picture.context)
            .load(prodDetail.imageUrl)
            .into(holder.binding.picture)
        holder.binding.prodName.text = prodDetail.name
        holder.binding.prodPrice.text = "$ " + prodDetail.price
        holder.binding.productCount.text = " Qty : " + prodDetail.qty
    }
}