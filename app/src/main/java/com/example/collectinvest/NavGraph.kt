package com.example.collectinvest

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.collectinvest.foryou.ForYou_screen
import com.example.collectinvest.goods.Goods_screen
import com.example.collectinvest.item.Item_screen
import com.example.collectinvest.login.Login_screen
import com.example.collectinvest.login.isUserLoggedIn
import com.example.collectinvest.models.ProductModel
import com.example.collectinvest.profile.Profile_screen
import com.example.collectinvest.search.Search_screen
import com.example.collectinvest.yourassets.YourAssets_screen
import com.google.gson.Gson
import com.google.gson.GsonBuilder

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
            YourAssets_screen(navController = navHostController)
        }

        composable("Search_screen"){
            Search_screen()
        }

    }

}