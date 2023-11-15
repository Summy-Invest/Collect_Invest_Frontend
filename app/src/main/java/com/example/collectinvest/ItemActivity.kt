package com.example.collectinvest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.collectinvest.item.Item_screen
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.profile.Profile_screen

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        val item : ProductModel? = intent.getParcelableExtra("item_data")
        setContent {
            Item_screen(item)
        }

    }
}