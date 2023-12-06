package com.example.collectinvest.bottom_nav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collectinvest.theme.MainTheme


// функция отображения нижней панели навигации
@Composable
fun BottomNavigation_fun(
    navController: NavController
){
    val listItems = listOf(
        BottomItem.ForYou,
        BottomItem.Goods,
        BottomItem.YourAssets
    )
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        // текущий экран
        val backStackEntry by navController.currentBackStackEntryAsState()
        // текущий роут
        val currRoute = backStackEntry?.destination?.route
        listItems.forEach{item ->
            BottomNavigationItem(
                // присваивание роута до выбранного экрана
                selected = currRoute == item.route,
                onClick = {
                    // переход
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(painter = painterResource(id = item.icon_id), contentDescription = "")
                },
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selectedContentColor = Color(146, 185,186),
                unselectedContentColor = Color.Black
            )


        }
    }
}