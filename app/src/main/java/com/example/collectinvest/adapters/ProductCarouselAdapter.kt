package com.example.collectinvest.adapters
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.ui.item.ItemFragment

class ProductCarouselAdapter(fragment: Fragment, private val products: List<ProductModel>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return products.size
    }

    override fun createFragment(position: Int): Fragment {
        val product = products[position]
        val itemFragment = ItemFragment.newInstance()

        // Передача данных о товаре во фрагмент через аргументы
        val args = Bundle()
        args.putString("imgUrl", product.imgUrl.toString())
        args.putString("name", product.name)
        args.putString("description", product.description)
        args.putDouble("price", product.price)
        args.putString("currency", product.currency.currencyCode)
        itemFragment.arguments = args

        return itemFragment
    }
}
