package com.example.collectinvest.foryou

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectinvest.NavGraph
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.utils.CardCreator


var prods = listOf(
    ProductModel(name = "Lisa1", imgUrl = "https://www.funnyart.club/uploads/posts/2023-02/thumbs/1675611210_www-funnyart-club-p-lisa-mem-yumor-20.jpg", description = "Hype", price = 300.0, currency = "USD"),
    ProductModel(name="Lisa2", imgUrl = "https://static.gtri.be/images/4cb4d2/ee3f884dae_m.jpg", description = "Cringe", price = 200.0, currency = "USD"),
    ProductModel(name="Lisa3", imgUrl = "https://s00.yaplakal.com/pics/pics_preview/1/2/0/6492021.jpg", description = "Rofl", price = 100.0, currency = "USD"))

@Composable
fun ForYou_screen(navController: NavHostController){

    Scaffold (
        content = {
            padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding()// Занимает всё доступное пространство экрана
            ) {
                Row (modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.SpaceBetween){
                    prods.forEachIndexed{
                            index, item -> CardCreator(item, navController)
                        if (index < prods.size - 1) {
                            Spacer(modifier = Modifier.width(100.dp))
                        }
                    }
                }
            }
        }
    )



}
