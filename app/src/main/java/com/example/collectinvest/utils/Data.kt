package com.example.collectinvest.utils

import com.example.collectinvest.models.ActualPriceModel
import com.example.collectinvest.models.BoughtAssetModel
import com.example.collectinvest.models.CategoryModel
import com.example.collectinvest.models.CollectibleModel
import com.example.collectinvest.models.TransactionModel
import com.example.collectinvest.models.UserModel
import com.example.collectinvest.models.WalletModel


var TopProds = listOf(
    CollectibleModel(Collectible_ID = 1, Name = "Lisa1", Description = "Hype", Photo = "https://www.funnyart.club/uploads/posts/2023-02/thumbs/1675611210_www-funnyart-club-p-lisa-mem-yumor-20.jpg", Category_ID = 1),
    CollectibleModel(Collectible_ID = 2, Name="Lisa2", Description = "Cringe", Photo = "https://static.gtri.be/images/4cb4d2/ee3f884dae_m.jpg", Category_ID = 1),
    CollectibleModel(Collectible_ID = 3, Name="Lisa3", Description ="Rofl", Photo = "https://s00.yaplakal.com/pics/pics_preview/1/2/0/6492021.jpg", Category_ID = 1)
)


//TODO вот эту еботу надо брать из бэка
var GoodsProds = listOf(
    CollectibleModel(Collectible_ID = 1, Name = "Lisa1", Description = "Hype", Photo = "https://www.funnyart.club/uploads/posts/2023-02/thumbs/1675611210_www-funnyart-club-p-lisa-mem-yumor-20.jpg", Category_ID = 1),
    CollectibleModel(Collectible_ID = 2, Name="Lisa2", Description = "Cringe", Photo = "https://static.gtri.be/images/4cb4d2/ee3f884dae_m.jpg", Category_ID = 1),
    CollectibleModel(Collectible_ID = 3, Name="Lisa3", Description ="Rofl", Photo = "https://s00.yaplakal.com/pics/pics_preview/1/2/0/6492021.jpg", Category_ID = 1),
    CollectibleModel(Collectible_ID = 4, Name = "Koshka1", Photo = "https://shutniks.com/wp-content/uploads/2019/11/smeshnoy_chernyy_kot_54_24000940.jpg", Description = "Koshka Egora Krida", Category_ID = 2),
    CollectibleModel(Collectible_ID = 5, Name="Koshka2", Photo = "https://do-slez.com/uploads/posts/2021-10/1633344226_4-7kcgdvqtxkc3kiqw8soyzpgb3gswxkahw8tdlfyus.jpg", Description = "Koshka Davy", Category_ID = 2),
    CollectibleModel(Collectible_ID = 6, Name="Koshka3", Photo = "https://silavmisli.ru/wp-content/uploads/2020/03/grumpy-cat_realgrumpycat-Instagram.jpg", Description = "Koshka Kirkorova", Category_ID = 2),
    CollectibleModel(Collectible_ID = 7, Name="Koshka4", Photo = "https://pic.rutubelist.ru/user/93/76/9376a62a123e3fc054abcc65ac1501e2.jpg", Description = "Koshka v plokhom kachestve", Category_ID = 2),
    CollectibleModel(Collectible_ID = 8, Name="Koshka5", Photo = "https://assets.faceit-cdn.net/teams_avatars/92b03b8c-5379-4e09-b568-0bac8c2907d3_1550804278525.jpg", Description="Zdorovaya koshka", Category_ID = 2),
    CollectibleModel(Collectible_ID = 9, Name="Koshka6", Photo = "https://cs12.pikabu.ru/post_img/big/2022/04/11/12/1649707861138498814.jpg", Description = "ustalaya koshka", Category_ID = 2),
    CollectibleModel(Collectible_ID = 10, Name = "Koshka7", Photo = "https://warframe.market/static/assets/user/avatar/620b280953e99b06525b12c2.png?26f178fb8efff386ade2a4b811d0d9a6", Description = "Koshka s tapkom", Category_ID = 2),
    CollectibleModel(Collectible_ID = 11, Name="Koshka8", Photo = "https://sun6-21.userapi.com/s/v1/if1/fhkhjGBqDRQpAXc5Rhcd_7nseHBkCkkmx3hhuViQROJU8x8NABd095XiI3AHy-YVnXgwr8Az.jpg?size=962x962&quality=96&crop=227,0,962,962&ava=1", Description = "Koshka s ulybkoy", Category_ID = 2),
    CollectibleModel(Collectible_ID = 12, Name="Koshka9", Photo = "https://i.vimeocdn.com/portrait/61741661_640x640", Description = "Koshka stremnaya", Category_ID = 2)
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

var ActualPrices= listOf(
    ActualPriceModel(Price_ID = 201, Price = 100.0, Collectible_ID = 1),
    ActualPriceModel(Price_ID = 202, Price = 120.0, Collectible_ID = 2),
    ActualPriceModel(Price_ID = 203, Price = 200.0, Collectible_ID = 3),
    ActualPriceModel(Price_ID = 204, Price = 130.0, Collectible_ID = 4),
    ActualPriceModel(Price_ID = 205, Price = 140.0, Collectible_ID = 5),
    ActualPriceModel(Price_ID = 206, Price = 105.0, Collectible_ID = 6),
    ActualPriceModel(Price_ID = 207, Price = 180.0, Collectible_ID = 7),
    ActualPriceModel(Price_ID = 208, Price = 300.0, Collectible_ID = 8),
    ActualPriceModel(Price_ID = 209, Price = 110.0, Collectible_ID = 9),
    ActualPriceModel(Price_ID = 210, Price = 160.0, Collectible_ID = 10),
    ActualPriceModel(Price_ID = 211, Price = 167.0, Collectible_ID = 11),
    ActualPriceModel(Price_ID = 212, Price = 500.0, Collectible_ID = 12)
)

var BoughtProducts = mutableListOf(
    BoughtAssetModel(Order_Id = 401, Order_date = "01.03.03", Count=1, Collectible_ID = 1, User_ID = 101, Transaction_id = 501),
    BoughtAssetModel(Order_Id = 402, Order_date = "01.03.03", Count=2, Collectible_ID = 4, User_ID = 101, Transaction_id = 502),
    BoughtAssetModel(Order_Id = 403, Order_date = "01.03.03", Count=1, Collectible_ID = 7, User_ID = 102, Transaction_id = 503),
    BoughtAssetModel(Order_Id = 404, Order_date = "01.03.03", Count=3, Collectible_ID = 5, User_ID = 102, Transaction_id = 504),
    BoughtAssetModel(Order_Id = 405, Order_date = "01.03.03", Count=1, Collectible_ID = 6, User_ID = 103, Transaction_id = 505),
    BoughtAssetModel(Order_Id = 406, Order_date = "01.03.03", Count=2, Collectible_ID = 9, User_ID = 103, Transaction_id = 506)
)

var Categories = listOf(
    CategoryModel(Category_ID = 1, Category_name = "Lisa", Category_type = "LisaType"),
    CategoryModel(Category_ID = 2, Category_name = "Koshka", Category_type = "KoshkaType")
)

var Transactions = mutableListOf(
    TransactionModel(Transaction_id = 501, Amount = 1, Status = "Buy", Wallet_id = 1),
    TransactionModel(Transaction_id = 502, Amount = 2, Status = "Buy", Wallet_id = 1),
    TransactionModel(Transaction_id = 503, Amount = 1, Status = "Buy", Wallet_id = 2),
    TransactionModel(Transaction_id = 504, Amount = 3, Status = "Buy", Wallet_id = 2),
    TransactionModel(Transaction_id = 505, Amount = 1, Status = "Buy", Wallet_id = 3),
    TransactionModel(Transaction_id = 506, Amount = 2, Status = "Buy", Wallet_id = 3)
)