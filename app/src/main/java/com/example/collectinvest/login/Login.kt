package com.example.collectinvest.login

import android.content.Context
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import com.example.collectinvest.entities.Message
import com.example.collectinvest.entities.user.AuthenticatedUser
import com.example.collectinvest.entities.user.User
import com.example.collectinvest.theme.darkgreen
import com.example.collectinvest.theme.white

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import com.example.collectinvest.utils.HttpClientSingleton
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking


// преференсес для сессии
private const val PREFS_NAME = "user_prefs"
private const val IS_LOGGED_IN = "is_logged_in"
private const val USER_ID = "user_id"
private const val USER_NAME = "user_name"



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

                val authenticatedUser: AuthenticatedUser;

                runBlocking {
                    try {
                        val client = HttpClientSingleton.client
                        val response: HttpResponse =
                            client.get("http://10.0.2.2:1111/userService/logIn/$email/$password")
                        when (response.status) {
                            HttpStatusCode.OK -> {
                                authenticatedUser = response.body<AuthenticatedUser>()

                                saveUserLoginStatus(
                                    context = activity,
                                    isLoggedIn = true,
                                    id = authenticatedUser.id,
                                    userName = authenticatedUser.name
                                )

                                activity.setContent {
                                    MainScreen(activity = activity)
                                }

                            }

                            else -> {
                                errorMessage = response.body<Message>().message
                            }
                        }
                    }catch(e: Throwable){
                        errorMessage = e.toString()
                    }
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

                                    val newAuthenticatedUser: AuthenticatedUser;

                                    runBlocking {
                                        try {
                                            val newUser: User = User(nameNew, emailNew, passwordNew)
                                            val client = HttpClientSingleton.client
                                            val response: HttpResponse =
                                                client.post("http://10.0.2.2:1111/userService/signUp")
                                                {
                                                    contentType(ContentType.Application.Json)
                                                    setBody(newUser)
                                                }
                                            when (response.status) {
                                                HttpStatusCode.OK -> {

                                                    newAuthenticatedUser =
                                                        response.body<AuthenticatedUser>()

                                                    saveUserLoginStatus(
                                                        context = activity,
                                                        isLoggedIn = true,
                                                        id = newAuthenticatedUser.id,
                                                        userName = newAuthenticatedUser.name
                                                    )

                                                    showDialog = false

                                                    activity.setContent {
                                                        MainScreen(activity = activity)
                                                    }

                                                }

                                                else -> {
                                                    errorMessageNew = response.toString()
                                                }
                                            }
                                        }catch (e: Throwable){
                                            errorMessage = e.toString()
                                        }
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
fun saveUserLoginStatus(context: Context, isLoggedIn: Boolean, id: Long, userName: String) {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
    editor.putLong(USER_ID, id)
    editor.putString(USER_NAME, userName)
    editor.apply()
}

// функция для проверки, вошел ли пользователь в систему
fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
}
