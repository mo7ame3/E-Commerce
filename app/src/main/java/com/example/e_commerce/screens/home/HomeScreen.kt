package com.example.e_commerce.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.e_commerce.navigation.AllScreens
import com.example.e_commerce.sharedpreference.SharedPreference
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sharedPreference = SharedPreference(context)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home" , modifier = Modifier.clickable {
            scope.launch {
                sharedPreference.removeToken()
                navController.navigate(AllScreens.LoginScreen.name){
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.popBackStack()
                }
            }
        })
    }
}