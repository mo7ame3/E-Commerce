package com.example.e_commerce.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.components.CircleInductor
import com.example.e_commerce.constant.Constant
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.home.Banner
import com.example.e_commerce.model.home.Home
import com.example.e_commerce.model.home.Product
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "ProduceStateDoesNotAssignValue", "CoroutineCreationDuringComposition",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val scope = rememberCoroutineScope()
    var loading by remember {
        mutableStateOf(true)
    }

    val bannerData = MutableStateFlow<List<Banner>>(emptyList())
    val productData = MutableStateFlow<List<Product>>(emptyList())

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

}

@Composable
fun ImageBanner(item: Banner) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.image), contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxSize()
                .aspectRatio(3f / 1.5f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    item: Product,
    homeViewModel: HomeViewModel
) {
    val isFav = remember {
        mutableStateOf(item.in_favorites)
    }
    val isCart = remember {
        mutableStateOf(item.in_cart)
    }
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.04f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = item.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Column {
                    Spacer(modifier = Modifier.height(5.dp))
                    if (item.discount > 0) {
                        Text(
                            text = "Discount " + item.discount + "%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = item.old_price.toString(),
                            style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = item.price.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )

                    } else {
                        Text(
                            text = item.price.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(15.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = 15.dp,
                mainAxisSize = SizeMode.Wrap
            ) {
                AssistChip(onClick = {
                    if (isFav.value) {
                        isFav.value = false
                        scope.launch {
                            val response = homeViewModel.favorites(
                                authorization = Constant.token,
                                productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isFav.value = true
                            }
                        }
                    } else {
                        isFav.value = true
                        scope.launch {
                            val response = homeViewModel.favorites(
                                authorization = Constant.token,
                                productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isFav.value = false
                            }
                        }
                    }
                },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = if (!isFav.value) Icons.Outlined.FavoriteBorder else Icons.Outlined.Favorite,
                            tint = if (!isFav.value) Color.Black else Color.Red,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = if (!isFav.value) "Mark as Favorite" else "Remove from Favorite")
                    }
                )

                AssistChip(onClick = {
                    if (isCart.value) {
                        isCart.value = false
                        scope.launch {
                            val response = homeViewModel.cart(
                                authorization = Constant.token,
                                productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isCart.value = true
                            }
                        }
                    } else {
                        isCart.value = true
                        scope.launch {
                            val response = homeViewModel.cart(
                                authorization = Constant.token,
                                productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isCart.value = false
                            }
                        }
                    }
                }, colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart, contentDescription = null,
                            tint = if (!isCart.value) Color.Black else Color.Red,
                        )
                    },
                    label = {
                        Text(text = if (!isCart.value) "Make in Cart" else "Remove from Cart")
                    }
                )

            }
        }
    }
}