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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.collectinvest.ItemActivity
import com.example.collectinvest.R
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.ui.item.ItemFragment
import com.squareup.picasso.Picasso

class ProductCarouselAdapter(private val dataList: List<ProductModel>) : RecyclerView.Adapter<ProductCarouselAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position % dataList.size] // Используйте остаток от деления, чтобы создать эффект бесконечной карусели
        holder.carouselText.text = item.description

        // Используйте Picasso или другую библиотеку для загрузки изображений
        Picasso.get().load(item.imgUrl.toString()).into(holder.carouselImage)
        val carouselButton: Button = holder.itemView.findViewById(R.id.carouselButton)
        carouselButton.setOnClickListener {
            // Здесь вы можете перейти на страницу товара и передать информацию о товаре
            val selectedItem = dataList[position % dataList.size]
            val pos = position % dataList.size
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
        return Int.MAX_VALUE // Задайте большое значение для создания впечатления бесконечности
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carouselImage: ImageView = itemView.findViewById(R.id.carouselImage)
        val carouselText: TextView = itemView.findViewById(R.id.carouselText)
    }
}