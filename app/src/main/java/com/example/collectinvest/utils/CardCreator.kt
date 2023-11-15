package com.example.collectinvest.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectinvest.ItemActivity
import com.example.collectinvest.NavGraph
import com.example.collectinvest.R
import com.example.collectinvest.models.ProductModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun CardCreator(item: ProductModel, context: Context){
    val name = item.name
    val imgUrl = item.imgUrl
    val price = item.price
    val curr = item.currency
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.clickable {
            val intt = Intent(context, ItemActivity::class.java)
            intt.putExtra("item_data", item)
            context.startActivity(intt)
        }
    ){
        Text(text = name);
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
        )
        Text(text = price.toString() + " " + curr)

    }

}