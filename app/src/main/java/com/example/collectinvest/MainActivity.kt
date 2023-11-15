package com.example.collectinvest


import android.os.Bundle

import androidx.activity.compose.setContent

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

import com.example.collectinvest.theme.MainTheme
import io.ktor.client.*
import io.ktor.client.engine.cio.*



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        setContent {
            //val navController_rem = rememberNavController()
            //NavGraph(navHostController = navController_rem)
            MainScreen(this)
        }

    }
}
