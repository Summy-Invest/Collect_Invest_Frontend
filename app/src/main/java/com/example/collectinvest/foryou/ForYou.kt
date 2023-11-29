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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.collectinvest.utils.CardCreator
import com.example.collectinvest.utils.GoodsProds
import com.example.collectinvest.utils.TopProds


@Composable
fun ForYou_screen(navController: NavHostController){
    val context = LocalContext.current
    Scaffold (
        content = {
            padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
            ) {
                Row (modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.SpaceBetween){
                    val Displayed = GoodsProds.subList(0, 3)
                    Displayed.forEachIndexed{
                            index, item -> CardCreator(item, context)
                        if (index < Displayed.size - 1) {
                            Spacer(modifier = Modifier.width(100.dp))
                        }
                    }
                }
            }
        }
    )
}
