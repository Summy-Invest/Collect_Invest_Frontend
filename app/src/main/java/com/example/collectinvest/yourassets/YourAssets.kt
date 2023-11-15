package com.example.collectinvest.yourassets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collectinvest.utils.GoodsProds
import com.example.collectinvest.utils.ScrollerCreator
import com.example.collectinvest.utils.TopProds
import com.example.collectinvest.utils.YourProds

@Composable
fun YourAssets_screen(navController: NavHostController){
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
                Column{
                    ScrollerCreator(prods = YourProds, context = context)
                }

            }
        },
        )
}