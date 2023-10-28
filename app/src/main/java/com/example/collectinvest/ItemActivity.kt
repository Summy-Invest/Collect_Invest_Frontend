package com.example.collectinvest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.ui.item.ItemFragment
import com.squareup.picasso.Picasso

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            // Извлекаем информацию о товаре из Bundle
            val productModel = bundle.getParcelable<ProductModel>("productModel")
            if (productModel != null) {
                // Теперь у вас есть объект productModel, содержащий данные о товаре
                // Используйте эту информацию для отображения данных о товаре на экране ItemActivity

                // Пример использования информации о товаре
                val productNameTextView = findViewById<TextView>(R.id.nameTextView)
                val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
                val priceTextView = findViewById<TextView>(R.id.priceTextView)

                productNameTextView.text = productModel.name
                descriptionTextView.text = productModel.description
                priceTextView.text = "${productModel.price} ${productModel.currency.currencyCode}"

                // Можете также использовать Picasso или другую библиотеку для загрузки изображения товара
                val imageViewProduct = findViewById<ImageView>(R.id.imageViewItem)
                Picasso.get().load(productModel.imgUrl.toString()).into(imageViewProduct)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}