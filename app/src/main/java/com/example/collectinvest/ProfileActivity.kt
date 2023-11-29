package com.example.collectinvest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.collectinvest.profile.Profile_screen

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        //val isLoggedIn = isUserLoggedIn(applicationContext)
        setContent {
            Profile_screen(this, applicationContext, MainActivity())
        }
    }

}