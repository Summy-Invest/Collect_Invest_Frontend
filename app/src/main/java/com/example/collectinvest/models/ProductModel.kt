package com.example.collectinvest.models

import io.ktor.http.Url
import java.util.Currency

data class ProductModel(
    val name: String,
    val imgUrl: Url,
    val description: String, //descrUrl: Url
    val price: Double, //priceUrl: Url
    val currency: Currency
)