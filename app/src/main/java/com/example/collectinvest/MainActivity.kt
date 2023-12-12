package com.example.collectinvest


import android.os.Bundle

import androidx.activity.compose.setContent

import androidx.appcompat.app.AppCompatActivity

import com.example.collectinvest.login.Login_screen
import com.example.collectinvest.login.isUserLoggedIn



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        val isLoggedIn = isUserLoggedIn(applicationContext)

        setContent {
            //Login_screen(activity = this)
            if (isLoggedIn) {
                MainScreen(activity = this)
            } else {
                
                Login_screen(activity = this)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
