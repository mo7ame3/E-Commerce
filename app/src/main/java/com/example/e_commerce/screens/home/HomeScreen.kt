package com.example.e_commerce.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.components.CircleInductor
import com.example.e_commerce.components.ImageCard
import com.example.e_commerce.constant.Constant
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.home.Banner
import com.example.e_commerce.model.home.Home
import com.example.e_commerce.model.home.Product
import com.example.e_commerce.navigation.AllScreens
import com.example.e_commerce.sharedpreference.SharedPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "ProduceStateDoesNotAssignValue",
    "CoroutineCreationDuringComposition",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val scope = rememberCoroutineScope()
    var loading by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val bannerData = MutableStateFlow<List<Banner>>(emptyList())
    val productData = MutableStateFlow<List<Product>>(emptyList())
    val sharedPreference = SharedPreference(context)
    val name = sharedPreference.getUserName.collectAsState(initial = "")
    if (Constant.token.isNotEmpty()) {
        val response: WrapperClass<Home, Boolean, Exception> =
            produceState<WrapperClass<Home, Boolean, Exception>>(initialValue = WrapperClass()) {
                value = homeViewModel.home(authorization = Constant.token)
            }.value
        if (response.data?.status == true) {
            scope.launch {
                bannerData.emit(response.data!!.data.banners)
                productData.emit(response.data!!.data.products)
                loading = false
            }
        }
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Spacer(Modifier.height(50.dp))
            Column(modifier = Modifier.padding(15.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = name.value.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                navController.navigate(route = AllScreens.FavoriteScreen.name)
                                drawerState.close()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(text = "Favorites", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                navController.navigate(route = AllScreens.CartScreen.name)
                                drawerState.close()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(Modifier.width(5.dp))
                    Text(text = "Cart", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                sharedPreference.removeToken()
                                navController.navigate(route = AllScreens.LoginScreen.name) {
                                    navController.popBackStack()
                                    navController.popBackStack()
                                    navController.popBackStack()
                                }
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(Modifier.width(5.dp))
                    Text(text = "Logout", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }, content = {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            if (!loading) {
                Column(modifier = Modifier.fillMaxSize()) {

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            ) {
                                items(bannerData.value) {
                                    ImageBanner(item = it)
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        items(productData.value) {
                            ImageCard(item = it, homeViewModel = homeViewModel)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            } else {
                CircleInductor()
            }
        }
    })
}


@Composable
fun ImageBanner(item: Banner) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.image),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxSize()
                .aspectRatio(3f / 1.5f)
        )
    }
}

