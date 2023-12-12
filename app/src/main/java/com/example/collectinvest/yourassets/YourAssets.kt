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
import com.example.collectinvest.entities.collectible.CollectibleItem
import com.example.collectinvest.utils.HttpClientSingleton
import com.example.collectinvest.utils.ScrollerCreator
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking


// экран купленного
@Composable
fun YourAssets_screen(navController: NavHostController) {
    val context = LocalContext.current
    // получение инфо о купленных товарах
    // запрос к апи
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = sharedPreferences.getString("email", "")
    val usr_id = sharedPreferences.getLong("user_id", 0)
    // СЮДА НАДО СПИСОК КУПЛЕННОГО ПО ЮЗЕР АЙДИ
    var assetsList by remember {
        mutableStateOf(GetAssets(usr_id))
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    assetsList = GetAssets(usr_id)
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

fun GetAssets(userId: Long): MutableList<CollectibleItem>{

    var res: MutableList<CollectibleItem>
    runBlocking {
        val client = HttpClientSingleton.client
        val response: HttpResponse = client.get("http://10.0.2.2:1111/collectibleService/getAllUserCollectibles/$userId")
        res = when (response.status) {
            HttpStatusCode.OK -> {
                response.body<List<CollectibleItem>>().toMutableList()
            }
            else -> {
                println(response.body<String>())
                emptyList<CollectibleItem>().toMutableList()
            }
        }
    }
    System.out.println(res.toString())
    System.out.println(userId)
    return res
}

