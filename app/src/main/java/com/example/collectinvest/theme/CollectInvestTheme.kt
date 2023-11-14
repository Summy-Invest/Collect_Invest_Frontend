package com.example.collectinvest.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.collectinvest.R

private val MainTheme = lightColors(
    primary = lightgreen,
    secondary = darkgreen,
    onSecondary = black,
    surface = lightgray
)

@Composable
fun MainTheme(
    content:@Composable ()-> Unit
){
    val colors = MainTheme
    MainThemeSet(colors, content)
}
@Composable
fun MainThemeSet(
    colors: Colors,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(

    ) {
        MaterialTheme(
            colors = colors,

            content = content
        )
    }
}