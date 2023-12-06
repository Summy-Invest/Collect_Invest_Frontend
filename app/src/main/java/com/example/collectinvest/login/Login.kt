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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectinvest.MainScreen
import com.example.collectinvest.models.UserModel
import com.example.collectinvest.models.WalletModel
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.white

import com.example.collectinvest.utils.Users
import com.example.collectinvest.utils.Wallets


// преференсес для сессии
private const val PREFS_NAME = "user_prefs"
private const val IS_LOGGED_IN = "is_logged_in"
private const val EMAIL = "email"


// экран входа
@Composable
fun Login_screen(activity: AppCompatActivity){

    var email by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    var errorMessage by remember { mutableStateOf("") }

// контент
    Box(modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "ВХОД", style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            )

            // вызов поля емейла
            LoginField(value = email, onValueChange = {email = it}, label = "Email", placeholder = "Введите email", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next))

            // вызов поля пароля
            LoginField(value = password, onValueChange = {password = it}, label = "Пароль", placeholder = "Введите пароль", visualTransformation = PasswordVisualTransformation())

            // забыли пароль
            Text(text = "Если Вы забыли пароль, напишите на почту pomogitelohu@gmail.com", style = TextStyle(
                fontFamily = FontFamily.Default,
                fontSize = 11.sp
            ))

            // кнопка входа
            Button(onClick = {
                // проверка на наличие такого юзера с таким паролем
                // запрос к апи
                val isValid = check_user(email, password)
                if (isValid){
                    // сохранение сессии входа (емейл в преференсес)
                    saveUserLoginStatus(context = activity, isLoggedIn = true, userEmail = email)

                    // установление контента приложения в главной активности
                    activity.setContent {
                        MainScreen(activity = activity)
                    }
                }
                else{
                    // ошибка
                    errorMessage = "Неверный email или пароль"
                } 


            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)) {
                Text(text = "Войти", color = white)
            }
            // показ ошибки
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }

            // создание аккаунта
            var showDialog by remember { mutableStateOf(false) }
            // кнопка popup'а
            TextButton(onClick = { showDialog = true }) {
                Text(text="Создать аккаунт", color = darkgreen)
            }

            // новый емейл
            var emailNew by remember{
                mutableStateOf("")
            }

            //новый пароль
            var passwordNew by remember{
                mutableStateOf("")
            }

            // новое имя
            var nameNew by remember{
                mutableStateOf("")
            }

            // ошибка при вводе емейла
            var errorMessageNew by remember { mutableStateOf("") }

            // popup
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { androidx.compose.material.Text("Создать аккаунт") },
                    text = {
                        Column {
                            // имя
                            OutlinedTextField(
                                value = nameNew,
                                onValueChange = { nameNew = it },
                                label = { Text(text = "Имя") },
                                placeholder = {Text(text = "Введите имя")},
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = darkgreen, // Цвет при фокусе
                                    unfocusedBorderColor = Color.Gray, // Цвет при отсутствии фокуса
                                    cursorColor = darkgreen // Цвет курсора
                                )
                            )

                            // вызов поля емейла
                            LoginField(value = emailNew, onValueChange = {emailNew = it}, label = "Email", placeholder = "Введите email", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next))
                            // вызов поля пароля
                            LoginField(value = passwordNew, onValueChange = {passwordNew = it}, label = "Пароль", placeholder = "Введите пароль", visualTransformation = PasswordVisualTransformation())


                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {

                                    // вызов проверки на корректность емейла

                                    //если все ок
                                    // запрос к апи
                                    if (check_email(emailNew)){
                                        // этой строчки не будет (получение последнего юзер айди из бд)
                                        var usr_id = Users.get(Users.size - 1).User_ID + 1

                                        // запись в юзеры
                                        Users.add(UserModel(User_ID = usr_id, Name = nameNew, Email = emailNew, Password = passwordNew))

                                        // этой строчки не будет (получение последнего айди кошелька из бд)
                                        var wallet_id = Wallets.get(Wallets.size - 1).Wallet_id + 1

                                        // запись в кошельки
                                        Wallets.add(WalletModel(Wallet_id = wallet_id, Money = 0.0, Status = "OK", User_id = usr_id))


                                        //  сохранение данных входа
                                        saveUserLoginStatus(context = activity, isLoggedIn = true, userEmail = emailNew)
                                        // закрытие
                                        showDialog = false

                                        // сразу вход в систему
                                        activity.setContent {
                                            MainScreen(activity = activity)
                                        }
                                    }
                                    else{
                                        // ошибка емейла
                                        errorMessageNew = "Неправильно написан email или такой email уже есть в базе"
                                    }

                                }, colors = ButtonDefaults.buttonColors(backgroundColor = darkgreen)
                            ) {
                                androidx.compose.material.Text("Создать и войти", color = white)
                            }

                            // для ошибки
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


// проверка на правильность емейла и пароля
// запрос к апи
private fun check_user(email: String, password: String): Boolean{
    val foundUser = Users.find { it.Email == email && it.Password == password }
    return foundUser != null
}

// проверка на правильно написанный емейл
private fun check_email(email: String): Boolean{
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
    return emailRegex.matches(email) && Users.find { it.Email == email } == null
}


// функция текстовых полей ввода
@Composable
fun LoginField(value: String,
               onValueChange: (String) -> Unit,
               label: String, placeholder: String,
               visualTransformation: VisualTransformation = VisualTransformation.None,
               keyboardOptions: KeyboardOptions = KeyboardOptions.Default
){
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = darkgreen, // Цвет при фокусе
        unfocusedBorderColor = Color.Gray, // Цвет при отсутствии фокуса
        cursorColor = darkgreen // Цвет курсора
    )
    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        colors = textFieldColors
    )
}


// помещение данных пользователя в преференсес и сохранение статуса входа
fun saveUserLoginStatus(context: Context, isLoggedIn: Boolean, userEmail: String) {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
    editor.putString(EMAIL, userEmail)
    editor.apply()
}

// функция для проверки, вошел ли пользователь в систему
fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
}




