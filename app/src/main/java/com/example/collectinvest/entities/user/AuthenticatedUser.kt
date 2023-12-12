package com.example.collectinvest.entities.user

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUser(
    val id: Long,
    val name: String
)