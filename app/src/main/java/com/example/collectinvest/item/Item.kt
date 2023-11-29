package com.example.collectinvest.item

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
import java.time.temporal.TemporalAmount

@Composable
fun Item_screen(item: CollectibleModel?, activity: AppCompatActivity){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Товар") },
                navigationIcon =  {
                    IconButton(onClick = {
                        activity.finish()
                        //navController.navigate("ForYou_screen")
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
                modifier = Modifier.padding()
            ) {
                item_container(item = item, context = activity)
            }

        }
    )

}

@Composable
fun item_container(item: CollectibleModel?, context: Context){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState())){
        val name = item?.Name.toString()
        Text(text = name)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item?.Photo.toString())
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp),
        )

        val descr = item?.Description.toString()
        Text(text = descr)
        val actualPrice = ActualPrices.find { it.Collectible_ID == item?.Collectible_ID }?.Price ?: 0.0
        Text(text = actualPrice.toString())
        val category = Categories.find { it.Category_ID == item?.Category_ID }?.Category_name
        Text(text = category.toString())
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", "")
        val usr_id = Users.find { it.Email == userEmail }?.User_ID ?: 0
        val count = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }?.Count ?: 0
        Text(text = "Куплено: ${count}")
        //Для продажи
        var isEnabled by remember { mutableStateOf(count > 0) }
        var showDialogSell by remember { mutableStateOf(false) }
        var inputTextSell by remember { mutableStateOf("") }
        //Для покупки
        var showDialogBuy by remember { mutableStateOf(false) }
        var inputTextBuy by remember { mutableStateOf("") }
        Row(){
            Button(onClick = { showDialogBuy = true }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                Text(text = "Купить", color = white)
            }
            Button(onClick = { showDialogSell = true}, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen), enabled = isEnabled) {
                Text(text = "Продать", color = white)
            }
        }

        var errorMessageBuy by remember { mutableStateOf("") }
        if (showDialogBuy) {
            AlertDialog(
                onDismissRequest = { showDialogBuy = false },
                title = { Text("Купить акции") },
                text = {
                    Column {
                        TextField(
                            value = inputTextBuy,
                            onValueChange = { inputTextBuy = it },
                            label = { Text("Количество акций") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val amount = inputTextBuy.toIntOrNull() ?: 0
                                val canBuy = CanBuy(usr_id, actualPrice, amount)
                                if (canBuy){
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


        // Для продажи
        var errorMessageSell by remember { mutableStateOf("") }
        if (showDialogSell) {
            AlertDialog(
                onDismissRequest = { showDialogSell = false },
                title = { Text("Купить акции") },
                text = {
                    Column {
                        TextField(
                            value = inputTextSell,
                            onValueChange = { inputTextSell = it },
                            label = { Text("Количество акций") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val amount = inputTextSell.toIntOrNull() ?: 0
                                val canSell = CanSell(count, amount)
                                if (canSell){

                                    SellFunc(item, usr_id, actualPrice, amount, count)
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

fun CanBuy(usr_id: Int?, actualPrice: Double, count: Int): Boolean{
    var money_to_spend = actualPrice * count
    var balance = Wallets.find { it.User_id == usr_id }?.Money ?: 0.0
    return (balance - money_to_spend) >= 0
}
fun BuyFunc(item: CollectibleModel?, usr_id: Int, actualPrice: Double, count: Int){
    //tranzactia
    var tr_id = Transactions.get(Transactions.size - 1).Transaction_id + 1
    Transactions.add(TransactionModel(Transaction_id = tr_id, Amount = count, Status = "Buy", Wallet_id = Wallets.find { it.User_id == usr_id }?.Wallet_id?:0))

    //Kupleno
    //est ili net
    if (BoughtProducts.find { it.Collectible_ID == item?.Collectible_ID && it.User_ID == usr_id } == null){
        var order_id = BoughtProducts.get(BoughtProducts.size - 1).Order_Id + 1
        BoughtProducts.add(BoughtAssetModel(Order_Id = order_id, Order_date = "01.03.23", Count = count, Collectible_ID = item?.Collectible_ID ?: 0, User_ID = usr_id, Transaction_id = tr_id))
    }
    else{
        val boughtToUpdate = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }
        boughtToUpdate?.let{
            it.Count += count
        }
    }
    val walletToUpdate = Wallets.find { it.User_id == usr_id }
    walletToUpdate?.let {
        it.Money -= actualPrice*count
    }

}

fun CanSell(usersCount: Int, intentCount:Int): Boolean{
    return usersCount >= intentCount
}

fun SellFunc(item: CollectibleModel?, usr_id: Int, actualPrice: Double, count: Int, ActualCount: Int){
    var tr_id = Transactions.get(Transactions.size - 1).Transaction_id + 1
    Transactions.add(TransactionModel(Transaction_id = tr_id, Amount = count, Status = "Sell", Wallet_id = Wallets.find { it.User_id == usr_id }?.Wallet_id?:0))
    //val juicy = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }
    //val jopa = ActualCount - count
    if (ActualCount - count > 0){
        val boughtToUpdate = BoughtProducts.find { it.User_ID == usr_id && it.Collectible_ID == item?.Collectible_ID }
        boughtToUpdate?.let{
            it.Count -= count
        }
    }
    else{
        BoughtProducts.removeIf { it.Collectible_ID == item?.Collectible_ID }
    }

    val walletToUpdate = Wallets.find { it.User_id == usr_id }
    walletToUpdate?.let {
        it.Money += actualPrice*count
    }
}