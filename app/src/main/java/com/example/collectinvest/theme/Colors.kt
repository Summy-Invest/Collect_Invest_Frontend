package com.example.collectinvest.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val lightgray = Color(232, 233, 235)
val white = Color(255, 255, 255)
val darkgreen = Color(60, 137, 137)
val lightgreen = Color(146, 185,186)
val black = Color(0, 0, 0)

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}