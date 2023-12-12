package com.example.collectinvest.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectinvest.MainActivity
import com.example.collectinvest.R
import com.example.collectinvest.entities.Message
import com.example.collectinvest.entities.financial.Wallet

import com.example.collectinvest.login.saveUserLoginStatus
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.lightgreen
import com.example.collectinvest.theme.white
import com.example.collectinvest.utils.ActualPrices
import com.example.collectinvest.utils.BoughtProducts
import com.example.collectinvest.utils.HttpClientSingleton
import com.example.collectinvest.utils.Users
import com.example.collectinvest.utils.Wallets
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


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
                    val username = sharedPreferences.getString("user_name", "")
                    val userId = sharedPreferences.getLong("user_id", 0)

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


                    var wallet: Wallet = Wallet(0,0,0.0,"0")
                    runBlocking {
                        try {
                            val client = HttpClientSingleton.client
                            val response: HttpResponse =
                                client.get("http://10.0.2.2:1111/financialService/getWallet/$userId")
                            when (response.status) {
                                HttpStatusCode.OK -> {
                                    wallet = response.body<Wallet>()
                                }
                                else -> {
                                    throw Throwable(response.body<Message>().message)
                                }
                            }
                        }catch (e: Throwable){
                            println(e.toString())
                        }
                    }

                    // баланс
                    // запрос к апи
                    var balance = wallet.balance
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

//                    var money_posses by remember { mutableStateOf(0.0) }
//
//                    LaunchedEffect(true) {
//                        while (true) {
//                            money_posses = CountAssetMoney(userId)
//                            delay(30 * 60 * 1000) // Пауза на 30 минут
//                        }
//                    }
//
//                    Row (modifier = Modifier.padding(20.dp)){
//                        Text(text = "Ваши активы: ", style = TextStyle(
//                            fontFamily = FontFamily.Default,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        ))
//                        Text(text = money_posses.toString() + " руб", style = TextStyle(
//                            fontFamily = FontFamily.Default,
//                            fontSize = 14.sp
//                        ))
//                    }


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

                                            runBlocking {
                                                try {
                                                    val client = HttpClientSingleton.client
                                                    val response: HttpResponse =
                                                        client.put("http://10.0.2.2:1111/financialService/topUp/$userId/$amount")
                                                    when (response.status) {
                                                        HttpStatusCode.OK -> {
                                                            println("ok")
                                                        }
                                                        else -> {
                                                            throw Throwable(response.body<Message>().message)
                                                        }
                                                    }
                                                }catch(e: Throwable){
                                                    println(e.toString())
                                                }
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
                        saveUserLoginStatus(context = context, isLoggedIn = false, id = 0, userName = "user_name")
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


//// функция подсчета активов кошелька
//// запрос к апи
//
//
//fun CountAssetMoney(user_id: Long?): Double{
//    return 13.37
//}


