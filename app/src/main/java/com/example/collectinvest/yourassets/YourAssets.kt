package com.example.collectinvest.yourassets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun YourAssets_screen(navController: NavHostController){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .padding()// Занимает всё доступное пространство экрана
    ) {
        Text(text = "Your Assets Screen")
    }
}