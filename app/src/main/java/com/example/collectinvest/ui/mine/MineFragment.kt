package com.example.collectinvest.ui.mine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collectinvest.R
import com.example.collectinvest.adapters.ProductVerticalAdapter
import com.example.collectinvest.databinding.FragmentMineBinding
import com.example.collectinvest.models.ProductModel
import io.ktor.http.Url
import java.util.Currency

class MineFragment : Fragment() {
    private var _binding: FragmentMineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var prods = listOf(
        ProductModel(name = "Koshka7", imgUrl= Url("https://warframe.market/static/assets/user/avatar/620b280953e99b06525b12c2.png?26f178fb8efff386ade2a4b811d0d9a6"), description = "Koshka s tapkom", price = 300.0, currency = Currency.getInstance("USD")),
        ProductModel(name="Koshka8", imgUrl = Url("https://sun6-21.userapi.com/s/v1/if1/fhkhjGBqDRQpAXc5Rhcd_7nseHBkCkkmx3hhuViQROJU8x8NABd095XiI3AHy-YVnXgwr8Az.jpg?size=962x962&quality=96&crop=227,0,962,962&ava=1"), description = "Koshka s ulybkoy", price = 200.0, Currency.getInstance("USD")),
        ProductModel(name="Koshka9", imgUrl = Url("https://i.vimeocdn.com/portrait/61741661_640x640"), description = "Koshka stremnaya", price = 100.0, currency = Currency.getInstance("USD")),
    )
    var summa = prods.sumByDouble { it.price }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val MineViewModel =
            ViewModelProvider(this).get(MineViewModel::class.java)

        _binding = FragmentMineBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textSumma
        textView.text = "Сумма ваших активов: ${summa}"
        val recyclerView: RecyclerView = binding.verticalRecyclerView

        // Используйте GridLayoutManager с параметром spanCount = 2
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        val adapter = ProductVerticalAdapter(prods)
        recyclerView.adapter = adapter
        return root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}