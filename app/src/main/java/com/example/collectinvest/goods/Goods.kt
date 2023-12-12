package com.example.collectinvest.goods

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collectinvest.utils.GoodsProds
import com.example.collectinvest.utils.ScrollerCreator


// экран товаров
@Composable
fun Goods_screen(navController: NavHostController){
    val context = LocalContext.current

    Scaffold (
        content = {
                padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, bottom = 40.dp)
            ) {
                // контент экрана
                // СЮДА НАДО ЗАПРОС НА ПОЛУЧЕНИЕ ВСЕХ ТОВАРОВ
                ScrollerCreator(prods = GoodsProds, context = context)
            }
        },

    )
}