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
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
