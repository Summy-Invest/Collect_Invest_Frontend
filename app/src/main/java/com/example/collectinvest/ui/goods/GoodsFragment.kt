package com.example.collectinvest.ui.goods

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collectinvest.adapters.ProductVerticalAdapter
import com.example.collectinvest.databinding.FragmentGoodsBinding
import com.example.collectinvest.models.ProductModel
import io.ktor.http.Url
import okhttp3.internal.immutableListOf
import java.util.Currency

class GoodsFragment : Fragment() {
    private var _binding: FragmentGoodsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var prods = listOf(
        ProductModel(name = "Koshka1", imgUrl= Url("https://shutniks.com/wp-content/uploads/2019/11/smeshnoy_chernyy_kot_54_24000940.jpg"), description = "Koshka Egora Krida", price = 300.0, currency = Currency.getInstance("USD")),
        ProductModel(name="Koshka2", imgUrl = Url("https://do-slez.com/uploads/posts/2021-10/1633344226_4-7kcgdvqtxkc3kiqw8soyzpgb3gswxkahw8tdlfyus.jpg"), description = "Koshka Davy", price = 200.0, Currency.getInstance("USD")),
        ProductModel(name="Koshka3", imgUrl = Url("https://silavmisli.ru/wp-content/uploads/2020/03/grumpy-cat_realgrumpycat-Instagram.jpg"), description = "Koshka Kirkorova", price = 100.0, currency = Currency.getInstance("USD")),
            ProductModel(name="Koshka4", imgUrl = Url("https://pic.rutubelist.ru/user/93/76/9376a62a123e3fc054abcc65ac1501e2.jpg"), description = "Koshka v plokhom kachestve", price = 50.0, currency = Currency.getInstance("USD")),
            ProductModel(name="Koshka5", imgUrl = Url("https://assets.faceit-cdn.net/teams_avatars/92b03b8c-5379-4e09-b568-0bac8c2907d3_1550804278525.jpg"), description="Zdorovaya koshka", price = 150.0, currency = Currency.getInstance("USD")),
            ProductModel(name="Koshka6", imgUrl = Url("https://cs12.pikabu.ru/post_img/big/2022/04/11/12/1649707861138498814.jpg"), description = "ustalaya koshka", price = 120.0, currency = Currency.getInstance("USD"))
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val GoodsViewModel =
            ViewModelProvider(this).get(GoodsViewModel::class.java)

        _binding = FragmentGoodsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.verticalRecyclerView

        // Используйте GridLayoutManager с параметром spanCount = 2
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        val adapter = ProductVerticalAdapter(prods)
        recyclerView.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}