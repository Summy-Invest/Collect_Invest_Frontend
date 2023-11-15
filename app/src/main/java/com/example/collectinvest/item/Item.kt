package com.example.collectinvest.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectinvest.R
import com.example.collectinvest.models.ProductModel

@Composable
fun Item_screen(item: ProductModel?){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "app bar title") },
                navigationIcon =  {
                    IconButton(onClick = {
                        navController.navigate("ForYou_screen") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            )
        },

        content = { padding ->
            Box(
                modifier = Modifier.padding()
            ) {
                item_container(item = item)
            }

        }
    )

}

@Composable
fun item_container(item: ProductModel?){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState())){
        val name = item?.name.toString()
        Text(text = name)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item?.imgUrl.toString())
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp),
        )
        val descr = item?.description.toString()
        Text(text = descr)
        Text(text = item?.price.toString() + " " + item?.currency.toString())
    }
}