package com.example.collectinvest.bottom_nav

import com.example.collectinvest.R


// элементы нижней панели навигации
sealed class BottomItem (val title: String, val icon_id: Int, val route: String){
    object ForYou: BottomItem("Для Вас", R.drawable.ic_for_you_24dp, "ForYou_screen")
    object Goods: BottomItem("Товары", R.drawable.ic_goods_24dp, "Goods_screen")
    object YourAssets: BottomItem("Мои активы", R.drawable.ic_mine_24dp, "YourAssets_screen")
}
