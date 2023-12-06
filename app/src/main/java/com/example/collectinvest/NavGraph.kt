package com.example.collectinvest

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collectinvest.foryou.ForYou_screen
import com.example.collectinvest.goods.Goods_screen
import com.example.collectinvest.search.Search_screen
import com.example.collectinvest.yourassets.YourAssets_screen

@Composable
fun NavGraph(
    navHostController: NavHostController,

){

    NavHost(navController = navHostController, startDestination = "ForYou_screen"){
        composable("ForYou_screen"){
            ForYou_screen(navController = navHostController)
        }
        composable("Goods_screen"){
            Goods_screen(navController = navHostController)
        }
        composable("YourAssets_screen"){
            YourAssets_screen()
        }

        composable("Search_screen"){
            Search_screen()
        }

    }

}