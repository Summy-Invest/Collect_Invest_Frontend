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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.collectinvest.utils.BoughtProducts

import com.example.collectinvest.utils.Users
import com.example.collectinvest.utils.Wallets

import kotlinx.coroutines.delay



// экран пользователя
@Composable
fun Profile_screen(activprof: AppCompatActivity, context: Context, mainact: AppCompatActivity){
    Scaffold(
        topBar = {
            // верхняя панель активности
            TopAppBar(
                title = { Text(text = "Ваш профиль") },
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
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    // получение имени пользователя по емейлу-> по айди
                    // запрос к апи??
                    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    val userEmail = sharedPreferences.getString("email", "")
                    //val username = sharedPreferences.getString("user_name", "")
                    val username = Users.find { it.Email == userEmail }?.Name
                    val usr_id = Users.find { it.Email == userEmail }?.User_ID

                    // имя
                    Text(text=username.toString(), style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    )

                    // дефолтная картинка
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Profile img default",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )

                    // баланс
                    // запрос к апи
                    var balance by remember { mutableStateOf(Wallets.find { it.User_id == usr_id }?.Money ?: 0.0) }
                    Row(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Ваш баланс: ", style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ))
                        Text(text = balance.toString() + " руб", style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontSize = 14.sp
                        ))
                    }

                    // активы в денежном эквиваленте
                    // запрос к апи
                    // обновление раз в 30 мин

                    var money_posses by remember { mutableStateOf(0.0) }

                    LaunchedEffect(true) {
                        while (true) {
                            money_posses = CountAssetMoney(usr_id)
                            delay(30 * 60 * 1000) // Пауза на 30 минут
                        }
                    }

                    Row (modifier = Modifier.padding(20.dp)){
                        Text(text = "Ваши активы: ", style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ))
                        Text(text = money_posses.toString() + " руб", style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontSize = 14.sp
                        ))
                    }


                    //

                    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkgreen, // Цвет при фокусе
                        unfocusedBorderColor = Color.Gray, // Цвет при отсутствии фокуса
                        cursorColor = darkgreen // Цвет курсора
                    )

                    var showDialog by remember { mutableStateOf(false) }
                    var inputText by remember { mutableStateOf("") }
                    Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth().padding(20.dp), colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                        androidx.compose.material3.Text(text = "Пополнить кошелек", color = white)
                    }

                    // пополнение кошелька
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Пополнить кошелек") },
                            text = {
                                Column {
                                    OutlinedTextField(
                                        value = inputText,
                                        onValueChange = { inputText = it },
                                        label = { Text(text = "Количество денег", style = TextStyle(color = Color.Gray)) },
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                        colors = textFieldColors
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = {
                                            val amount = inputText.toDoubleOrNull() ?: 0.0
                                            balance += amount
                                            // обновление данных в кошельке
                                            // запрос к апи
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

                    // выход из сессии
                    // сброс преференсес
                    Button(onClick = {
                        saveUserLoginStatus(context = context, isLoggedIn = false, userEmail = "")
                        mainact.finish()
                        val intent = Intent(activprof, MainActivity::class.java)
                        activprof.startActivity(intent)
                        activprof.finish()

                    }, modifier = Modifier.fillMaxWidth().padding(20.dp), colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                        androidx.compose.material3.Text(text = "Выйти", color = white)
                    }
                }

            }
        }
    )
}


// функция подсчета активов кошелька
// СЮДА ПОЙДЕТ СПИСОК КУПЛЕННОГО

// запрос к апи
fun CountAssetMoney(user_id: Int?): Double{
    var filtered = BoughtProducts.filter { it.User_ID == user_id }
    var money_posess = 0.0
    for (el in filtered){
        var act_price = 0
        money_posess += act_price * el.Count
    }
    return money_posess
}


