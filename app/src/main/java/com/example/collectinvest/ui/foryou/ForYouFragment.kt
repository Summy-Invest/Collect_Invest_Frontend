package com.example.collectinvest.ui.foryou

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ForYouViewModel =
            ViewModelProvider(this).get(ForYouViewModel::class.java)

        _binding = FragmentForYouBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button: Button = binding.buttonForYou
        button.setOnClickListener {
            val intt = Intent(requireContext(), ItemActivity::class.java)
            startActivity(intt)
        }
        val carouselView: CarouselView = binding.carouselView

        carouselView.setPageCount(sampleImgs.size)
        carouselView.setImageListener(imageListener)
        carouselView.stopCarousel()
        return root
    }
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            val realPosition = position % sampleImgs.size
            Picasso.get().load(sampleImgs[realPosition]).into(imageView)
        }
    }


override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}