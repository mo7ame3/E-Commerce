package com.example.e_commerce.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.screens.cart.CartScreen
import com.example.e_commerce.screens.cart.CartViewModel
import com.example.e_commerce.screens.favorites.FavoriteScreen
import com.example.e_commerce.screens.favorites.FavoriteViewModel
import com.example.e_commerce.screens.home.HomeScreen
import com.example.e_commerce.screens.home.HomeViewModel
import com.example.e_commerce.screens.login.LoginScreen
import com.example.e_commerce.screens.login.LoginViewModel
import com.example.e_commerce.screens.register.RegisterScreen
import com.example.e_commerce.screens.register.RegisterViewModel
import com.example.e_commerce.screens.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AllScreens.SplashScreen.name
    ) {

        composable(route = AllScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(route = AllScreens.LoginScreen.name) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }

        composable(route = AllScreens.HomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        composable(route = AllScreens.RegisterScreen.name) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(navController = navController, registerViewModel = registerViewModel)
        }
        composable(route = AllScreens.FavoriteScreen.name) {
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(navController = navController, favoriteViewModel = favoriteViewModel)
        }
        composable(route = AllScreens.CartScreen.name) {
            val cartViewModel = hiltViewModel<CartViewModel>()
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }

    }
}