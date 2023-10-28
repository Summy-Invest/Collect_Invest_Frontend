package com.example.collectinvest.ui.item

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.collectinvest.R
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.ui.profile.ProfileViewModel
import com.squareup.picasso.Picasso

class ItemFragment : Fragment() {
    private lateinit var viewModel: ItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Получаем переданный объект ProductModel из Intent
        """val productModel = arguments?.getSerializable("productModel") as ProductModel

        // Находим элементы макета и устанавливаем информацию о товаре
        val imageView = view.findViewById<ImageView>(R.id.imageViewItem)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val priceTextView = view.findViewById<TextView>(R.id.priceTextView)

        Picasso.get().load(productModel.imgUrl.toString()).into(imageView)
        descriptionTextView.text = productModel.description"""
        //priceTextView.text = "Price: ${productModel.price} ${productModel.currency.currencyCode}"

        return inflater.inflate(R.layout.fragment_item, container, false)
    }
    fun updateProductInfo(productModel: ProductModel) {
        // Здесь обновите UI вашего фрагмента, чтобы отобразить информацию о товаре
        // Например, установите тексты и изображения в соответствии с выбранным товаром
        // Например:
        val nameTextView = view?.findViewById<TextView>(R.id.nameTextView)
        nameTextView?.text = productModel.name
        val descriptionTextView = view?.findViewById<TextView>(R.id.descriptionTextView)
        descriptionTextView?.text = productModel.description
        val priceTextView = view?.findViewById<TextView>(R.id.priceTextView)
        priceTextView?.text = "${productModel.price} ${productModel.currency.currencyCode}"
        val imageView = view?.findViewById<ImageView>(R.id.imageViewItem)
        Picasso.get().load(productModel.imgUrl.toString()).into(imageView)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
