package com.example.collectinvest.entities.user

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val name: String,
    val email: String,
    val password: String
)