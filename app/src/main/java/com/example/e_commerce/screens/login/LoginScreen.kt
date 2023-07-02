package com.example.e_commerce.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_commerce.components.CircleInductor
import com.example.e_commerce.components.EmailInput
import com.example.e_commerce.components.LoginButton
import com.example.e_commerce.components.PasswordInput
import com.example.e_commerce.constant.Constant
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.loginAndRegister.LoginAndRegister
import com.example.e_commerce.navigation.AllScreens
import com.example.e_commerce.sharedpreference.SharedPreference
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    var loading by remember {
        mutableStateOf(false)
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val eye = remember {
        mutableStateOf(false)
    }
    val emailError = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sharedPreference = SharedPreference(context)
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(modifier = Modifier.fillMaxSize()) {
        if (!loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Login", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                EmailInput(email = email, onAction = KeyboardActions {
                    keyboardController?.hide()
                }, error = emailError)
                Spacer(modifier = Modifier.height(15.dp))
                PasswordInput(
                    password = password,
                    eye = eye,
                    onButtonAction = { eye.value = !eye.value },
                    onAction = KeyboardActions {
                        if (!emailError.value && password.value.isNotBlank()) {
                            loading = true
                            scope.launch {
                                val response: WrapperClass<LoginAndRegister, Boolean, Exception> =
                                    loginViewModel.login(
                                        email = email.value,
                                        password = password.value
                                    )
                                if (response.data?.status == true) {
                                    loading = false
                                    sharedPreference.saveToken(token = response.data!!.data.token)
                                    sharedPreference.saveUserName(token = response.data!!.data.name)
                                    Constant.token = response.data!!.data.token
                                    navController.navigate(route = AllScreens.HomeScreen.name) {
                                        navController.popBackStack()
                                        navController.popBackStack()
                                        navController.popBackStack()
                                    }
                                } else {
                                    loading = false
                                    Toast.makeText(
                                        context,
                                        response.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                keyboardController?.hide()
                            }
                        }
                    })
                Spacer(modifier = Modifier.height(15.dp))
                LoginButton(label = "Login") {
                    if (!emailError.value && password.value.isNotBlank()) {
                        loading = true
                        scope.launch {
                            val response: WrapperClass<LoginAndRegister, Boolean, Exception> =
                                loginViewModel.login(
                                    email = email.value,
                                    password = password.value
                                )
                            if (response.data?.status == true) {
                                loading = false
                                sharedPreference.saveToken(token = response.data!!.data.token)
                                sharedPreference.saveUserName(token = response.data!!.data.name)
                                Constant.token = response.data!!.data.token
                                navController.navigate(route = AllScreens.HomeScreen.name) {
                                    navController.popBackStack()
                                    navController.popBackStack()
                                    navController.popBackStack()
                                }
                            } else {
                                loading = false
                                Toast.makeText(
                                    context,
                                    response.data?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    crossAxisAlignment = FlowCrossAxisAlignment.Center,
                    mainAxisAlignment = FlowMainAxisAlignment.Center,
                    mainAxisSpacing = 1.dp,
                    mainAxisSize = SizeMode.Wrap
                ) {
                    Text(
                        "don't have account?", style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    )
                    Text("Register now",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red
                        ), modifier = Modifier.clickable {
                            navController.navigate(route = AllScreens.RegisterScreen.name)
                        }
                    )
                }
            }
        } else {
            CircleInductor()
        }
    }
}