package com.example.collectinvest.utils

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(context: Context) {
    private val dataStore = context.createDataStore(name = "user_prefs")
    companion object{
        val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL")
        val USER_PASSWORD_KEY = preferencesKey<String>("USER_PASSWORD")
    }

    suspend fun storeUser(email: String, password: String){
        dataStore.edit {
            it[USER_EMAIL_KEY] = email
            it[USER_PASSWORD_KEY] = password
        }
    }
    //coroutines
    val userEmailFlow: Flow<String> = dataStore.data.map {
        it[USER_EMAIL_KEY] ?: ""
    }


    val userPasswordFlow: Flow<String> = dataStore.data.map {
        it[USER_PASSWORD_KEY] ?: ""
    }
}