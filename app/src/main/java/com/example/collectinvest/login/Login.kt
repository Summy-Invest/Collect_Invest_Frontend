package com.example.collectinvest.login

import android.content.Context
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.collectinvest.MainScreen

import com.example.collectinvest.utils.Users

private const val PREFS_NAME = "user_prefs"
private const val IS_LOGGED_IN = "is_logged_in"

@Composable
fun Login_screen(activity: AppCompatActivity){

    //val navController = rememberNavController()
    var username by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }


    Box(modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            //Image(painter = painterResource(id = R.drawable.ic_text_logo), contentDescription = "logo")
            Text(text = "ВХОД")
            LoginField(value = username, onValueChange = {username = it}, label = "Email", placeholder = "Введите email", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next))
            LoginField(value = password, onValueChange = {password = it}, label = "Пароль", placeholder = "Введите пароль", visualTransformation = PasswordVisualTransformation())
            Text(text = "Если Вы забыли пароль, напишите на почту pomogitelohu@gmail.com")
            Button(onClick = {
                             val isValid = check_user(username, password)
                if (isValid){
                    saveUserLoginStatus(context = activity, isLoggedIn = true)
                    activity.setContent {
                        MainScreen(activity = activity)
                    }
                }

            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Войти")
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text="Создать аккаунт")
            }
        }
    }
}

private fun check_user(email: String, password: String): Boolean{
    val foundUser = Users.find { it.email == email && it.password == password }
    return foundUser != null
}
@Composable
fun LoginField(value: String,
               onValueChange: (String) -> Unit,
               label: String, placeholder: String,
               visualTransformation: VisualTransformation = VisualTransformation.None,
               keyboardOptions: KeyboardOptions = KeyboardOptions.Default
){
    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}

fun saveUserLoginStatus(context: Context, isLoggedIn: Boolean) {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
    editor.apply()
}

// Функция для проверки, вошел ли пользователь в систему
fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
}

