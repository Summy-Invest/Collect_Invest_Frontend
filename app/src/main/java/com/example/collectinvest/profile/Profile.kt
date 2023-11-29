package com.example.collectinvest.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.lightgreen
import com.example.collectinvest.theme.white
import com.example.collectinvest.utils.SessionManager
import com.example.collectinvest.utils.SessionManager.Companion.USER_EMAIL_KEY
import com.example.collectinvest.utils.Users
import com.example.collectinvest.utils.Wallets

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.http.ContentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun Profile_screen(activprof: AppCompatActivity, context: Context, mainact: AppCompatActivity){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon =  {

                        IconButton(onClick = {
                            activprof.finish()

                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }

                }, backgroundColor = lightgreen
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
                    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    val userEmail = sharedPreferences.getString("email", "")
                    val username = Users.find { it.Email == userEmail }?.Name
                    val usr_id = Users.find { it.Email == userEmail }?.User_ID
                    Text(text=username.toString())
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

                    var balance by remember { mutableStateOf(Wallets.find { it.User_id == usr_id }?.Money ?: 0.0) }
                    Text(text = balance.toString())
                    var showDialog by remember { mutableStateOf(false) }
                    var inputText by remember { mutableStateOf("") }
                    Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                        androidx.compose.material3.Text(text = "Пополнить кошелек", color = white)
                    }
                    // пополнение кошелька
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Пополнить кошелек") },
                            text = {
                                Column {
                                    TextField(
                                        value = inputText,
                                        onValueChange = { inputText = it },
                                        label = { Text("Количество денег") },
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = {
                                            val amount = inputText.toDoubleOrNull() ?: 0.0
                                            balance += amount
                                            // сам список кошельков не обновляется, тут будет запрос на обновление бд
                                            val walletToUpdate = Wallets.find { it.User_id == usr_id }
                                            walletToUpdate?.let {
                                                it.Money = balance
                                            }
                                            showDialog = false
                                        }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                                    ) {
                                        Text("Пополнить", color = white)
                                    }
                                }
                            },
                            confirmButton = {
                               //она не нужна
                            },
                            dismissButton = {
                                Button(
                                    onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                                ) {
                                    Text("Закрыть", color = white)
                                }
                            }
                        )
                    }

                    Button(onClick = {
                        saveUserLoginStatus(context = context, isLoggedIn = false, userEmail = "")
                        mainact.finish()
                        val intent = Intent(activprof, MainActivity::class.java)
                        activprof.startActivity(intent)
                        activprof.finish()

                    }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                        androidx.compose.material3.Text(text = "Выйти", color = white)
                    }
                }

            }
        }
    )
}

@Composable
fun BalancePopUP(showDialog: Boolean){

}


