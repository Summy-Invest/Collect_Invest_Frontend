package com.example.collectinvest.item

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectinvest.R
import com.example.collectinvest.entities.collectible.BuySellRequest
import com.example.collectinvest.models.CollectibleModel
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.lightgreen
import com.example.collectinvest.theme.white
import com.example.collectinvest.utils.HttpClientSingleton
import invest.collect.com.entities.collectible.UserShares
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking


// экран товара
// принимает элемент класса Collectible
@Composable
fun Item_screen(item: CollectibleModel?, activity: AppCompatActivity){
    Scaffold(
        // верхняя панель
        topBar = {
            TopAppBar(
                title = { Text(text = "Товар") },
                // кнопка назад
                // завершает активити товара
                navigationIcon =  {
                    IconButton(onClick = {
                        activity.finish()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }, backgroundColor = lightgreen
            )
        },
        // контент экрана
        content = { padding ->
            Box(
                modifier = Modifier.padding()
            ) {
                item_container(item = item, context = activity)
            }

        }
    )

}


// функция для отображения товара
@Composable
fun item_container(item: CollectibleModel?, context: Context){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally){
        // название
        Text(text = item!!.name, style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ), modifier = Modifier.padding(20.dp)
        )
        // картинка
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.photoUrl.toString())
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .padding(20.dp)
        )
        // описание
        Text(text = "Описание: " + item.description.toString(), style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))

        // актуальная цена
        val actualPrice = item.currentPrice
        Text(text = "Актуальная цена: " + actualPrice.toString() + " руб", style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))

        // название категории
        val category = item.category
        Text(text = "Категория: "+category.toString(), style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))

        // количество купленных акций
        // будет запрос к апи (таблица купленных акционок по юзер айди)
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getLong("user_id", 0)

        var count by remember {
            mutableStateOf(0)
        }

        runBlocking {
            try {
                val client = HttpClientSingleton.client
                val response: HttpResponse =
                    client.get("http://10.0.2.2:1111/collectibleService/getUserCollectibles/$userId/${item.id}")
                when (response.status) {
                    HttpStatusCode.OK -> {
                        count = response.body<UserShares>().shares
                    }

                    else -> {
                        println(response.body<String>())
                    }
                }
            } catch (e: Throwable) {
                println(e.toString())
            }
        }






        Text(text = "Куплено: ${count}", style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))


        //для popup для продажи
        var isEnabled by remember { mutableStateOf(count > 0) }
        var showDialogSell by remember { mutableStateOf(false) }
        var inputTextSell by remember { mutableStateOf("") }


        //для popup для покупки
        var showDialogBuy by remember { mutableStateOf(false) }
        var inputTextBuy by remember { mutableStateOf("") }

        // кнопки для покупки и продажи
        Row(modifier = Modifier.padding(20.dp)){
            Button(onClick = { showDialogBuy = true }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                Text(text = "Купить", color = white)
            }
            Button(onClick = { showDialogSell = true}, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen), enabled = isEnabled) {
                Text(text = "Продать", color = white)
            }
        }

        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = darkgreen, // Цвет при фокусе
            unfocusedBorderColor = Color.Gray, // Цвет при отсутствии фокуса
            cursorColor = darkgreen // Цвет курсора
        )

        // popup для покупки
        var errorMessageBuy by remember { mutableStateOf("") }
        if (showDialogBuy) {
            AlertDialog(
                onDismissRequest = { showDialogBuy = false },
                title = { Text("Купить акции") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = inputTextBuy,
                            onValueChange = { inputTextBuy = it },
                            label = {
                                Text(text = "Количество акций", style = TextStyle(color = Color.Gray))
                                    },
                            placeholder = {Text(text = "Введите количество акций")},
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            colors = textFieldColors
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {


                                val amount = inputTextBuy.toIntOrNull() ?: 0

                                runBlocking {
                                    try {
                                        val buyRequest = BuySellRequest(item.id, userId, amount)
                                        val client = HttpClientSingleton.client
                                        val response: HttpResponse =
                                            client.post("http://10.0.2.2:1111/collectibleService/buy")
                                            {
                                                contentType(ContentType.Application.Json)
                                                setBody(buyRequest)
                                            }
                                        when (response.status) {
                                            HttpStatusCode.OK -> {
                                                isEnabled = true
                                                showDialogBuy = false
                                            }

                                            else -> {
                                                errorMessageBuy = response.body<String>()
                                            }
                                        }
                                    } catch (e: Throwable) {
                                        errorMessageBuy = e.toString()
                                    }
                                }

                            }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                        ) {
                            Text("Купить", color = white)
                        }
                        if (errorMessageBuy.isNotEmpty()) {
                            androidx.compose.material3.Text(errorMessageBuy, color = Color.Red)
                        }
                    }
                },
                confirmButton = {
                    //она не нужна
                },
                dismissButton = {
                    Button(
                        onClick = { showDialogBuy = false }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                    ) {
                        Text("Закрыть", color = white)
                    }
                }
            )
        }


        // popup для продажи
        var errorMessageSell by remember { mutableStateOf("") }
        if (showDialogSell) {
            AlertDialog(
                onDismissRequest = { showDialogSell = false },
                title = { Text("Продать акции") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = inputTextSell,
                            onValueChange = { inputTextSell = it },
                            label = { Text(text = "Количество акций", style = TextStyle(color = Color.Gray))},
                            placeholder = {Text(text = "Введите количество акций")},
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            colors = textFieldColors
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {

                                val amount = inputTextSell.toIntOrNull() ?: 0

                                var sellRequest: BuySellRequest = BuySellRequest(item.id, userId, amount)

                                runBlocking {
                                    try {
                                        val client = HttpClientSingleton.client
                                        val response: HttpResponse = client.post("http://10.0.2.2:1111/collectibleService/sell")
                                        {
                                            contentType(ContentType.Application.Json)
                                            setBody(sellRequest)
                                        }
                                        when (response.status) {
                                            HttpStatusCode.OK -> {
                                                isEnabled = true
                                                showDialogSell = false
                                            }

                                            else -> {
                                                errorMessageSell = response.body<String>()
                                            }
                                        }
                                    } catch (e: Throwable) {
                                        errorMessageSell = e.toString()
                                    }
                                }
                            }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                        ) {
                            Text("Продать", color = white)
                        }
                        if (errorMessageSell.isNotEmpty()) {
                            androidx.compose.material3.Text(errorMessageSell, color = Color.Red)
                        }
                    }
                },
                confirmButton = {
                    //она не нужна
                },
                dismissButton = {
                    Button(
                        onClick = { showDialogSell = false }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                    ) {
                        Text("Закрыть", color = white)
                    }
                }
            )
        }
    }
}