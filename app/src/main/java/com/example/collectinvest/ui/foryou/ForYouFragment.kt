package com.example.collectinvest.ui.foryou

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.collectinvest.ItemActivity
import com.example.collectinvest.adapters.ProductCarouselAdapter
import com.example.collectinvest.databinding.FragmentForYouBinding
import com.squareup.picasso.Picasso
import com.example.collectinvest.models.ProductModel
import com.synnapps.carouselview.CarouselView
import io.ktor.http.Url
import java.util.Currency

import com.synnapps.carouselview.ImageListener

class ForYouFragment : Fragment() {
    private var _binding: FragmentForYouBinding? = null
    private val binding get() = _binding!!
    var sampleImgs = arrayOf("https://www.funnyart.club/uploads/posts/2023-02/thumbs/1675611210_www-funnyart-club-p-lisa-mem-yumor-20.jpg",
        "https://static.gtri.be/images/4cb4d2/ee3f884dae_m.jpg",
        "https://s00.yaplakal.com/pics/pics_preview/1/2/0/6492021.jpg")
    var prods = listOf(ProductModel(name = "Lisa1", imgUrl=Url("https://www.funnyart.club/uploads/posts/2023-02/thumbs/1675611210_www-funnyart-club-p-lisa-mem-yumor-20.jpg"), description = "Hype", price = 300.0, currency = Currency.getInstance("USD")),
        ProductModel(name="Lisa2", imgUrl = Url("https://static.gtri.be/images/4cb4d2/ee3f884dae_m.jpg"), description = "Cringe", price = 200.0, Currency.getInstance("USD")),
        ProductModel(name="Lisa3", imgUrl = Url("https://s00.yaplakal.com/pics/pics_preview/1/2/0/6492021.jpg"), description = "Rofl", price = 100.0, currency = Currency.getInstance("USD")))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ForYouViewModel =
            ViewModelProvider(this).get(ForYouViewModel::class.java)

        _binding = FragmentForYouBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.carouselRecyclerView
        val adapter = ProductCarouselAdapter(prods)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        return root
    }



override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}