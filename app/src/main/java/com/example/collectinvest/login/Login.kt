package com.example.collectinvest.login

import android.content.Context
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.collectinvest.MainScreen
import com.example.collectinvest.models.UserModel
import com.example.collectinvest.models.WalletModel
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.white

import com.example.collectinvest.utils.Users
import com.example.collectinvest.utils.Wallets

private const val PREFS_NAME = "user_prefs"
private const val IS_LOGGED_IN = "is_logged_in"
private const val EMAIL = "email"

@Composable
fun Login_screen(activity: AppCompatActivity){

    //val navController = rememberNavController()
    var email by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    var errorMessage by remember { mutableStateOf("") }


    Box(modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "ВХОД")
            LoginField(value = email, onValueChange = {email = it}, label = "Email", placeholder = "Введите email", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next))
            LoginField(value = password, onValueChange = {password = it}, label = "Пароль", placeholder = "Введите пароль", visualTransformation = PasswordVisualTransformation())
            Text(text = "Если Вы забыли пароль, напишите на почту pomogitelohu@gmail.com")
            Button(onClick = {
                             val isValid = check_user(email, password)
                if (isValid){
                    saveUserLoginStatus(context = activity, isLoggedIn = true, userEmail = email)
                    activity.setContent {
                        MainScreen(activity = activity)
                    }
                }
                else{
                    errorMessage = "Неверный email или пароль"
                } 


            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                Text(text = "Войти", color = white)
            }
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }

            var showDialog by remember { mutableStateOf(false) }
            TextButton(onClick = { showDialog = true }) {
                Text(text="Создать аккаунт", color = darkgreen)
            }

            var emailNew by remember{
                mutableStateOf("")
            }
            var passwordNew by remember{
                mutableStateOf("")
            }
            var nameNew by remember{
                mutableStateOf("")
            }

            var errorMessageNew by remember { mutableStateOf("") }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { androidx.compose.material.Text("Создать аккаунт") },
                    text = {
                        Column {

                            TextField(
                                value = nameNew,
                                onValueChange = { nameNew = it },
                                label = { androidx.compose.material.Text("Имя") },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                            LoginField(value = emailNew, onValueChange = {emailNew = it}, label = "Email", placeholder = "Введите email", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next))
                            LoginField(value = passwordNew, onValueChange = {passwordNew = it}, label = "Пароль", placeholder = "Введите пароль", visualTransformation = PasswordVisualTransformation())


                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (check_email(emailNew)){
                                        var usr_id = Users.get(Users.size - 1).User_ID + 1
                                        Users.add(UserModel(User_ID = usr_id, Name = nameNew, Email = emailNew, Password = passwordNew))
                                        var wallet_id = Wallets.get(Wallets.size - 1).Wallet_id + 1
                                        Wallets.add(WalletModel(Wallet_id = wallet_id, Money = 0.0, Status = "OK", User_id = usr_id))
                                        showDialog = false
                                    }
                                    else{
                                        errorMessageNew = "Неправильно написан email или такой email уже есть в базе"
                                    }

                                }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                            ) {
                                androidx.compose.material.Text("Создать", color = white)
                            }
                            if (errorMessageNew.isNotEmpty()) {
                                Text(errorMessageNew, color = Color.Red)
                            }
                        }
                    },
                    confirmButton = {
                        //она не нужна
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                        ) {
                            androidx.compose.material.Text("Закрыть", color = white)
                        }
                    }
                )
            }



        }
    }
}

private fun check_user(email: String, password: String): Boolean{
    val foundUser = Users.find { it.Email == email && it.Password == password }
    return foundUser != null
}

private fun check_email(email: String): Boolean{
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
    return emailRegex.matches(email) && Users.find { it.Email == email } == null
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

fun saveUserLoginStatus(context: Context, isLoggedIn: Boolean, userEmail: String) {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
    editor.putString(EMAIL, userEmail)
    editor.apply()
}

// Функция для проверки, вошел ли пользователь в систему
fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
}




