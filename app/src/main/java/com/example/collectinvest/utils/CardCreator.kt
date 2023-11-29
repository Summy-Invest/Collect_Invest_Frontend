package com.example.collectinvest.utils

import android.content.Context
import android.content.Intent
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectinvest.ItemActivity
import com.example.collectinvest.R
import com.example.collectinvest.models.CollectibleModel

@Composable
fun CardCreator(item: CollectibleModel, context: Context){
    val name = item.Name
    val imgUrl = item.Photo
    val actualPrice = ActualPrices.find { it.Collectible_ID == item.Collectible_ID }?.Price
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
        Text(text = actualPrice.toString())


    }

}