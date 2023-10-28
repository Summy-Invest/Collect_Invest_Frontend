package com.example.collectinvest.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.collectinvest.ItemActivity
import com.example.collectinvest.R
import com.example.collectinvest.models.ProductModel
import com.squareup.picasso.Picasso

class ProductVerticalAdapter(private val dataList: List<ProductModel>) : RecyclerView.Adapter<ProductVerticalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = dataList[position]
        holder.productName.text = product.name
        holder.productPrice.text = "${product.price} ${product.currency.currencyCode}"

        // Используйте Picasso или другую библиотеку для загрузки изображений
        Picasso.get().load(product.imgUrl.toString()).into(holder.productImage)
        val carouselButton: Button = holder.itemView.findViewById(R.id.carouselButton)
        carouselButton.setOnClickListener {
            // Здесь вы можете перейти на страницу товара и передать информацию о товаре
            val selectedItem = dataList[position]
            val pos = position
            val nomen = selectedItem.name
            val activity = holder.itemView.context as AppCompatActivity

            // Создайте намерение для перехода на страницу товара (ItemActivity) и передайте информацию о товаре
            val intent = Intent(activity, ItemActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("productModel", selectedItem)
            intent.putExtras(bundle)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.carouselName)
        val productImage: ImageView = itemView.findViewById(R.id.carouselImage)
        val productPrice: TextView = itemView.findViewById(R.id.carouselPrice)
    }
}
