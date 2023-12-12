package com.example.collectinvest.utils

import com.example.collectinvest.entities.collectible.CollectibleItem
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking



var GoodsProds = mutableListOf<CollectibleItem>()

fun updateCollectible(){
    runBlocking {
        try {
            val client = HttpClientSingleton.client
            val response: HttpResponse =
                client.get("http://10.0.2.2:1111/collectibleService/getAllCollectibles")
            when (response.status) {
                HttpStatusCode.OK -> {
                    GoodsProds = response.body<MutableList<CollectibleItem>>()
                }

                else -> {
                    println(response.body<String>())
                }
            }
        }catch(e: Throwable){
            println("a[osjdfowenqoifnqeroifneqr")
        }
    }
}