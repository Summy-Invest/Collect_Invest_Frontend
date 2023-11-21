package com.example.collectinvest.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectinvest.MainActivity
import com.example.collectinvest.MainScreen
import com.example.collectinvest.R
import com.example.collectinvest.login.Login_screen
import com.example.collectinvest.login.saveUserLoginStatus

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

@Composable
fun Profile_screen(activprof: AppCompatActivity, context: Context, mainact: AppCompatActivity){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "app bar title") },
                navigationIcon =  {

                        IconButton(onClick = {
                            activprof.finish()

                        }) {
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
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
            ) {
                Column {
                    val img_url = "https://kartinki.pibig.info/uploads/posts/2023-04/1682323864_kartinki-pibig-info-p-obezyana-za-kompyuterom-kartinki-arti-inst-24.jpg"
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(img_url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = stringResource(R.string.app_name),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp),
                    )
                    Text(text = "Profile Screen")

                    Button(onClick = {
                        saveUserLoginStatus(context = context, isLoggedIn = false)
                        /*activitymain.setContent {
                            Login_screen(activity = activitymain)
                        }*/
                        mainact.finish()
                        val intent = Intent(activprof, MainActivity::class.java)
                        activprof.startActivity(intent)
                        activprof.finish()


                    }, modifier = Modifier.fillMaxWidth()) {
                        androidx.compose.material3.Text(text = "Выйти")
                    }
                }

            }
        }
    )
}
