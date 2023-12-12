package com.example.collectinvest.utils

import com.example.collectinvest.entities.collectible.CollectibleItem
import com.example.collectinvest.models.ActualPriceModel
import com.example.collectinvest.models.BoughtAssetModel
import com.example.collectinvest.models.CategoryModel
import com.example.collectinvest.models.CollectibleModel
import com.example.collectinvest.models.TransactionModel
import com.example.collectinvest.models.UserModel
import com.example.collectinvest.models.WalletModel


var GoodsProds = listOf(
    CollectibleItem(id = 1, name = "Lisa1", description = "Hype", photoUrl = "https://www.funnyart.club/uploads/posts/2023-02/thumbs/1675611210_www-funnyart-club-p-lisa-mem-yumor-20.jpg", category = "Lisa", currentPrice = 100.0, availableShares = 100),
    CollectibleItem(id = 2, name="Lisa2", description = "Cringe", photoUrl = "https://static.gtri.be/images/4cb4d2/ee3f884dae_m.jpg", category = "Lisa", currentPrice = 120.0, availableShares = 100),
    CollectibleItem(id = 3, name="Lisa3", description ="Rofl", photoUrl = "https://s00.yaplakal.com/pics/pics_preview/1/2/0/6492021.jpg", category = "Lisa", currentPrice = 140.0, availableShares = 100),
    CollectibleItem(id = 4, name = "Koshka1", photoUrl = "https://shutniks.com/wp-content/uploads/2019/11/smeshnoy_chernyy_kot_54_24000940.jpg", description = "Koshka Egora Krida", category = "Koshka", currentPrice = 130.0, availableShares = 100),
    CollectibleItem(id = 5, name="Koshka2", photoUrl = "https://do-slez.com/uploads/posts/2021-10/1633344226_4-7kcgdvqtxkc3kiqw8soyzpgb3gswxkahw8tdlfyus.jpg", description = "Koshka Davy", category = "Koshka", currentPrice = 200.0, availableShares = 100),
    CollectibleItem(id = 6, name="Koshka3", photoUrl = "https://silavmisli.ru/wp-content/uploads/2020/03/grumpy-cat_realgrumpycat-Instagram.jpg", description = "Koshka Kirkorova", category = "Koshka", currentPrice = 150.0, availableShares = 100),
    CollectibleItem(id = 7, name="Koshka4", photoUrl = "https://pic.rutubelist.ru/user/93/76/9376a62a123e3fc054abcc65ac1501e2.jpg", description = "Koshka v plokhom kachestve", category = "Koshka", currentPrice = 160.0, availableShares = 100),
    CollectibleItem(id = 8, name="Koshka5", photoUrl = "https://assets.faceit-cdn.net/teams_avatars/92b03b8c-5379-4e09-b568-0bac8c2907d3_1550804278525.jpg", description="Zdorovaya koshka", category = "Koshka", currentPrice = 170.0, availableShares = 100),
    CollectibleItem(id = 9, name="Koshka6", photoUrl = "https://cs12.pikabu.ru/post_img/big/2022/04/11/12/1649707861138498814.jpg", description = "ustalaya koshka", category = "Koshka", currentPrice = 180.0, availableShares = 100),
    CollectibleItem(id = 10, name = "Koshka7", photoUrl = "https://warframe.market/static/assets/user/avatar/620b280953e99b06525b12c2.png?26f178fb8efff386ade2a4b811d0d9a6", description = "Koshka s tapkom", category = "Koshka", currentPrice = 190.0, availableShares = 100),
    CollectibleItem(id = 11, name="Koshka8", photoUrl = "https://sun6-21.userapi.com/s/v1/if1/fhkhjGBqDRQpAXc5Rhcd_7nseHBkCkkmx3hhuViQROJU8x8NABd095XiI3AHy-YVnXgwr8Az.jpg?size=962x962&quality=96&crop=227,0,962,962&ava=1", description = "Koshka s ulybkoy", category = "Koshka", currentPrice = 210.0, availableShares = 100),
    CollectibleItem(id = 12, name="Koshka9", photoUrl = "https://i.vimeocdn.com/portrait/61741661_640x640", description = "Koshka stremnaya", category = "Koshka", currentPrice = 220.0, availableShares = 100)
    )


var Users = mutableListOf(
    UserModel(User_ID = 101, Name = "Евлампий", Email = "musipusi@mail.ru", Password = "Babushka"),
    UserModel(User_ID = 102, Name = "Алевтина", Email = "terminator666@mail.ru", Password = "Dedushka"),
    UserModel(User_ID = 103, Name = "Aboba", Email = "a", Password = "a")
)

var Wallets = mutableListOf(
    WalletModel(Wallet_id = 1, Money = 1000.0, Status = "OK", User_id = 101),
    WalletModel(Wallet_id = 2, Money = 1500.0, Status = "OK", User_id = 102),
    WalletModel(Wallet_id = 3, Money = 2000.0, Status = "OK", User_id = 103)
)

var BoughtProducts = mutableListOf(
    BoughtAssetModel(Order_Id = 401, Order_date = "01.03.03", Count=1, Collectible_ID = 1, User_ID = 101, Transaction_id = 501),
    BoughtAssetModel(Order_Id = 402, Order_date = "01.03.03", Count=2, Collectible_ID = 4, User_ID = 101, Transaction_id = 502),
    BoughtAssetModel(Order_Id = 403, Order_date = "01.03.03", Count=1, Collectible_ID = 7, User_ID = 102, Transaction_id = 503),
    BoughtAssetModel(Order_Id = 404, Order_date = "01.03.03", Count=3, Collectible_ID = 5, User_ID = 102, Transaction_id = 504),
    BoughtAssetModel(Order_Id = 405, Order_date = "01.03.03", Count=1, Collectible_ID = 6, User_ID = 103, Transaction_id = 505),
    BoughtAssetModel(Order_Id = 406, Order_date = "01.03.03", Count=2, Collectible_ID = 9, User_ID = 103, Transaction_id = 506)
)

var Transactions = mutableListOf(
    TransactionModel(Transaction_id = 501, Amount = 1, Status = "Buy", Wallet_id = 1),
    TransactionModel(Transaction_id = 502, Amount = 2, Status = "Buy", Wallet_id = 1),
    TransactionModel(Transaction_id = 503, Amount = 1, Status = "Buy", Wallet_id = 2),
    TransactionModel(Transaction_id = 504, Amount = 3, Status = "Buy", Wallet_id = 2),
    TransactionModel(Transaction_id = 505, Amount = 1, Status = "Buy", Wallet_id = 3),
    TransactionModel(Transaction_id = 506, Amount = 2, Status = "Buy", Wallet_id = 3)
)