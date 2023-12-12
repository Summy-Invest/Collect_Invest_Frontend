package com.example.collectinvest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.example.collectinvest.bottom_nav.BottomNavigation_fun
import com.example.collectinvest.theme.lightgreen
import com.example.collectinvest.utils.updateCollectible
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(activity: AppCompatActivity){
    val navController_rem = rememberNavController()

    updateCollectible()
    LaunchedEffect(true) {
        while (true) {
            updateCollectible()
            delay(10 * 1000) // Пауза на 10 сек
        }
    }

    Scaffold(
        bottomBar = {
           BottomNavigation_fun(navController = navController_rem)
        },
        topBar = {

            TopAppBar (
                title = {
                Text(text="collectinvest")
            },
                actions = {
                    IconButton(onClick = {
                        val intt = Intent(activity, ProfileActivity::class.java)
                        activity.startActivity(intt)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_profile_24dp), contentDescription = "")
                    }
                }, backgroundColor = lightgreen
            )

        }
    ){
        NavGraph(navHostController = navController_rem)
    }
}