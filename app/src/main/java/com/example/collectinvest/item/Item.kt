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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectinvest.R
import com.example.collectinvest.models.BoughtAssetModel
import com.example.collectinvest.models.CollectibleModel
import com.example.collectinvest.models.TransactionModel
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.lightgreen
import com.example.collectinvest.theme.white
import com.example.collectinvest.utils.ActualPrices
import com.example.collectinvest.utils.BoughtProducts
import com.example.collectinvest.utils.Categories
import com.example.collectinvest.utils.Transactions
import com.example.collectinvest.utils.Users
import com.example.collectinvest.utils.Wallets


// экран товара
// принимает элемент класса Collectible
@Composable
fun Item_screen(item: CollectibleModel?, activity: AppCompatActivity){
    val navController = rememberNavController()
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
        Text(text = item?.Name.toString(), style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ), modifier = Modifier.padding(20.dp)
        )
        // картинка
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item?.Photo.toString())
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
        Text(text = "Описание: " + item?.Description.toString(), style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))

        // актуальная цена
        // будет запрос к апи для получения акт. цены по айди
        val actualPrice = ActualPrices.find { it.Collectible_ID == item?.Collectible_ID }?.Price ?: 0.0
        Text(text = "Актуальная цена: " + actualPrice.toString() + " руб", style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))

        // название категории
        // аналогично цене
        val category = Categories.find { it.Category_ID == item?.Category_ID }?.Category_name
        Text(text = "Категория: "+category.toString(), style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp), modifier = Modifier.padding(20.dp))

        // количество купленных акций
        // будет запрос к апи (таблица купленных акционок по юзер айди)
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", "")
        val usr_id = Users.find { it.Email == userEmail }?.User_ID ?: 0
        val count = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }?.Count ?: 0
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
                                // количество и проверка на кошелек
                                val amount = inputTextBuy.toIntOrNull() ?: 0
                                val canBuy = CanBuy(usr_id, actualPrice, amount)
                                if (canBuy){
                                    // вызов функции покупки
                                    BuyFunc(item, usr_id, actualPrice, amount)
                                    isEnabled = true
                                    showDialogBuy = false
                                }
                                else{
                                    errorMessageBuy = "Недостаточно средств"
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
                                // количество и проверка на возможность продать
                                val amount_to_sell = inputTextSell.toIntOrNull() ?: 0
                                val canSell = CanSell(count, amount_to_sell)
                                if (canSell){
                                    // функция продажи
                                    SellFunc(item, usr_id, actualPrice, amount_to_sell, count)

                                    // проверка на наличие акций этого предмета у юзера
                                    // если акций нет - кнопка продать отключается
                                    // запрос к апи?
                                    if (BoughtProducts.find { it.Collectible_ID == item?.Collectible_ID && it.User_ID ==usr_id } ==null)
                                        isEnabled = false
                                    showDialogSell = false
                                }
                                else{
                                    errorMessageSell = "Недостаточно акций"
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


// проверка на возможность купить
// баланс юзера по айди
// запрос к кошелькам?
fun CanBuy(usr_id: Int?, actualPrice: Double, count: Int): Boolean{
    var money_to_spend = actualPrice * count
    var balance = Wallets.find { it.User_id == usr_id }?.Money ?: 0.0
    return (balance - money_to_spend) >= 0
}


// функция покупки
fun BuyFunc(item: CollectibleModel?, usr_id: Int, actualPrice: Double, count: Int){
    // транзакция
    // этой строчки не будет (получение индекса последней транзакции)
    var tr_id = Transactions.get(Transactions.size - 1).Transaction_id + 1
    // добавление новой транзакции
    // запрос к апи на добавление записи в транзакции
    Transactions.add(TransactionModel(Transaction_id = tr_id, Amount = count, Status = "Buy", Wallet_id = Wallets.find { it.User_id == usr_id }?.Wallet_id?:0))

    // проверка на наличие акций этого товара у юзера
    // если нет, то в таблицу купленных добавляется новая запись
    // запрос к апи
    if (BoughtProducts.find { it.Collectible_ID == item?.Collectible_ID && it.User_ID == usr_id } == null){
        var order_id = BoughtProducts.get(BoughtProducts.size - 1).Order_Id + 1
        BoughtProducts.add(BoughtAssetModel(Order_Id = order_id, Order_date = "01.03.23", Count = count, Collectible_ID = item?.Collectible_ID ?: 0, User_ID = usr_id, Transaction_id = tr_id))
    }
    // если есть, то в таблице купленных обновляется количество
    //запрос к апи
    else{
        val boughtToUpdate = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }
        boughtToUpdate?.let{
            it.Count += count
        }
    }

    // обновление кошелька
    // запрос к апи
    val walletToUpdate = Wallets.find { it.User_id == usr_id }
    walletToUpdate?.let {
        it.Money -= actualPrice*count
    }

}


// проверка на возможность продать
fun CanSell(usersCount: Int, intentCount:Int): Boolean{
    return usersCount >= intentCount
}

// функция продажи
fun SellFunc(item: CollectibleModel?, usr_id: Int, actualPrice: Double, count: Int, ActualCount: Int){

    // транзакция
    // этой строчки не будет (получение индекса последней транзакции)
    var tr_id = Transactions.get(Transactions.size - 1).Transaction_id + 1

    // добавление транзакции
    // запрос к апи
    Transactions.add(TransactionModel(Transaction_id = tr_id, Amount = count, Status = "Sell", Wallet_id = Wallets.find { it.User_id == usr_id }?.Wallet_id?:0))

    // количество акций предмета после продажи
    // если больше 0 - в таблице купленного меняется кол-во
    // запрос к апи
    if (ActualCount - count > 0){
        val boughtToUpdate = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }
        boughtToUpdate?.let{
            it.Count -= count
        }
    }
    // если 0 - из таблицы купленных удаляется запись о владении юзера предметом
    // запрос к апи
    else{
        BoughtProducts.removeIf { it.Collectible_ID == item?.Collectible_ID  && it.User_ID == usr_id}
    }

    // обновление кошелька
    // запрос к апи
    val walletToUpdate = Wallets.find { it.User_id == usr_id }
    walletToUpdate?.let {
        it.Money += actualPrice*count
    }
}