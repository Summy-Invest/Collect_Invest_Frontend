package com.example.collectinvest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectinvest.bottom_nav.BottomNavigation_fun


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(activity: AppCompatActivity){
    val navController_rem = rememberNavController()
    Scaffold(
        bottomBar = {
           BottomNavigation_fun(navController = navController_rem)
        },
        topBar = {

            TopAppBar (
                title = {
                Text(text="Govno")
            },
                actions = {
                    IconButton(onClick = {
                        navController_rem.navigate("Search_screen")
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_search_24dp), contentDescription = "")
                    }
                    IconButton(onClick = {
                        val intt = Intent(activity, ProfileActivity::class.java)
                        activity.startActivity(intt)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_profile_24dp), contentDescription = "")
                    }
                }
            )

        }
    ){
        NavGraph(navHostController = navController_rem)
    }
}