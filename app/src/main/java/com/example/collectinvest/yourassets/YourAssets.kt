package com.example.collectinvest.yourassets

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.collectinvest.models.CollectibleModel
import com.example.collectinvest.utils.BoughtProducts
import com.example.collectinvest.utils.GoodsProds
import com.example.collectinvest.utils.ScrollerCreator

import com.example.collectinvest.utils.Users



// экран купленного
@Composable
fun YourAssets_screen(navController: NavHostController) {
    val context = LocalContext.current
    // получение инфо о купленных товарах
    // запрос к апи
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = sharedPreferences.getString("email", "")
    val usr_id = Users.find { it.Email == userEmail }?.User_ID

    var assetsList by remember {
        mutableStateOf(GetAssets(usr_id))
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                assetsList = GetAssets(usr_id)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, bottom = 40.dp)
            ) {
                Column {
                    ScrollerCreator(prods = assetsList, context = context)
                }
            }
        },
    )
}

//TODO все колекционки пользователя
// запрос к апи
fun GetAssets(usr_id: Int?): List<CollectibleModel>{

    var UserAssetsIds =  BoughtProducts.filter{ it.User_ID == usr_id  && it.Count > 0}.map { it.Collectible_ID }
    var UserAssets = GoodsProds.filter { it.Collectible_ID in UserAssetsIds }
    return UserAssets
}

