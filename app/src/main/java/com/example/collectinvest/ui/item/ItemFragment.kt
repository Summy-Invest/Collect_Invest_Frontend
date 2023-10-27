package com.example.collectinvest.ui.item

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.collectinvest.R
import com.squareup.picasso.Picasso

class ItemFragment : Fragment() {
    private lateinit var imgUrl: String
    private lateinit var name: String
    private lateinit var description: String
    private var price: Double = 0.0
    private lateinit var currency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imgUrl = it.getString("imgUrl", "")
            name = it.getString("name", "")
            description = it.getString("description", "")
            price = it.getDouble("price", 0.0)
            currency = it.getString("currency", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        return view
    }

    companion object {
        fun newInstance() = ItemFragment()
    }
}
