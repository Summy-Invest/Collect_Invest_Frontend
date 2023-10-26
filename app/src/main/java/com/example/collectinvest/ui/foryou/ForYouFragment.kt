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
import com.example.collectinvest.ItemActivity
import com.example.collectinvest.databinding.FragmentForYouBinding
import com.squareup.picasso.Picasso

class ForYouFragment : Fragment() {
    private var _binding: FragmentForYouBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //
        val ForYouViewModel =
            ViewModelProvider(this).get(ForYouViewModel::class.java)

        _binding = FragmentForYouBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textForYou
        //val imageView: ImageView = binding.imgForYou
        ForYouViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val button: Button = binding.buttonForYou
        button.setOnClickListener {
            val intt = Intent(requireContext(), ItemActivity::class.java)
            startActivity(intt)
        }
        """val imgurl = "https://sun9-21.userapi.com/impf/c302413/v302413443/5d49/SYC9TgJhnIo.jpg?size=960x640&quality=96&sign=5c2e23bd777c56930a4bf246ba16735a&c_uniq_tag=mrjI63bIJJ1wotwjvbQk6Rc47NZzc72P8XvxZqsjFRI&type=album"
        Picasso.get()
            .load(imgurl)
            .into(imageView)"""
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}