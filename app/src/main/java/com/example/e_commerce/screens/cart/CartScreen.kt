package com.example.e_commerce.screens.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce.components.CircleInductor
import com.example.e_commerce.components.ImageCardFavAndCart
import com.example.e_commerce.constant.Constant
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.cart.get.CartItem
import com.example.e_commerce.model.cart.get.GetCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val scope = rememberCoroutineScope()
    var loading by remember {
        mutableStateOf(true)
    }
    val productData = MutableStateFlow<List<CartItem>>(emptyList())

    if (Constant.token.isNotEmpty()) {
        val response: WrapperClass<GetCart, Boolean, Exception> =
            produceState<WrapperClass<GetCart, Boolean, Exception>>(initialValue = WrapperClass()) {
                value = cartViewModel.getCart(authorization = Constant.token)
            }.value
        if (response.data?.status == true) {
            scope.launch {
                productData.emit(response.data!!.data.cart_items)
                loading = false
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        topBar = {
            Row {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }
    ) {
        if (!loading) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (!productData.value.isNullOrEmpty()) {
                    LazyColumn {
                        items(productData.value) {
                            ImageCardFavAndCart(cartItem = it)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "لا يوجد عناصر في السلة",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        } else {
            CircleInductor()
        }
    }

}