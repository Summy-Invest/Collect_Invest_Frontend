package com.example.collectinvest.entities.financial

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val id: Long,
    val status: String
)