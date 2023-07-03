package com.example.e_commerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce.R
import com.example.e_commerce.constant.Constant
import com.example.e_commerce.model.cart.get.CartItem
import com.example.e_commerce.model.favorite.get.DataX
import com.example.e_commerce.model.home.Product
import com.example.e_commerce.screens.home.HomeViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.launch

@Composable
fun CircleInductor() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Email,
    error: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default,
    isSingleLine: Boolean = true,
) {
    val EMAIL_REGEX = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = "البريد الالكتروني") },
        value = email.value,
        onValueChange = {
            email.value = it
            error.value = !EMAIL_REGEX.toRegex().matches(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red,
            errorCursorColor = Color.Red,
            errorLabelColor = Color.Red,
        ),
        singleLine = isSingleLine,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
        },
        isError = error.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String,
    text: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Email,
    onAction: KeyboardActions = KeyboardActions.Default,
    isSingleLine: Boolean = true,
    imageVector: ImageVector
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label) },
        value = text.value,
        onValueChange = {
            text.value = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        singleLine = isSingleLine,
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = null)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Password,
    isSingleLine: Boolean = true,
    eye: MutableState<Boolean>,
    onButtonAction: () -> Unit,
    onAction: KeyboardActions = KeyboardActions.Default,
    label: String = "كلمة السر",
) {
    val visualTransformation = if (eye.value) VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(singleLine = isSingleLine,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label) },
        value = password.value,
        onValueChange = {
            if (it.isNotBlank()) {
                password.value = it
            } else {
                password.value = ""
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { onButtonAction.invoke() }) {
                if (eye.value) Icon(
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = null, modifier = Modifier.size(20.dp)
                )
                else Icon(
                    painter = painterResource(id = R.drawable.visibilityoff),
                    contentDescription = null, modifier = Modifier.size(20.dp)
                )
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)

        })
}

@Composable
fun LoginButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = CircleShape,
        modifier = Modifier.width(130.dp),
    ) {
        Text(
            text = label
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    item: Product, homeViewModel: HomeViewModel
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
                                authorization = Constant.token, productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isFav.value = true
                            }
                        }
                    } else {
                        isFav.value = true
                        scope.launch {
                            val response = homeViewModel.favorites(
                                authorization = Constant.token, productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isFav.value = false
                            }
                        }
                    }
                }, colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ), leadingIcon = {
                    Icon(
                        imageVector = if (!isFav.value) Icons.Outlined.FavoriteBorder else Icons.Outlined.Favorite,
                        tint = if (!isFav.value) Color.Black else Color.Red,
                        contentDescription = null
                    )
                }, label = {
                    Text(text = if (!isFav.value) "Mark as Favorite" else "Remove from Favorite")
                })

                AssistChip(onClick = {
                    if (isCart.value) {
                        isCart.value = false
                        scope.launch {
                            val response = homeViewModel.cart(
                                authorization = Constant.token, productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isCart.value = true
                            }
                        }
                    } else {
                        isCart.value = true
                        scope.launch {
                            val response = homeViewModel.cart(
                                authorization = Constant.token, productId = item.id.toString()
                            )
                            if (response.data?.status == false) {
                                isCart.value = false
                            }
                        }
                    }
                }, colors = AssistChipDefaults.assistChipColors(
                    leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ), leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart, contentDescription = null,
                        tint = if (!isCart.value) Color.Black else Color.Red,
                    )
                }, label = {
                    Text(text = if (!isCart.value) "Make in Cart" else "Remove from Cart")
                })

            }
        }
    }
}

@Composable
fun ImageCardFavAndCart(
    favItem: DataX? = null,
    cartItem: CartItem? = null
) {
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
            if (favItem != null) {
                Text(
                    text = favItem.product.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
            } else if (cartItem != null) {
                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                if (favItem != null) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = favItem.product.image
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (cartItem != null) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = cartItem.product.image
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column {
                    Spacer(modifier = Modifier.height(5.dp))
                    if (favItem != null) {
                        if (favItem.product.discount > 0) {
                            Text(
                                text = "Discount " + favItem.product.discount + "%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Red
                            )

                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = favItem.product.old_price.toString(),
                                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = favItem.product.price.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )

                        } else {
                            Text(
                                text = favItem.product.price.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    } else if (cartItem != null) {
                        if (cartItem.product.discount > 0) {
                            Text(
                                text = "Discount " + cartItem.product.discount + "%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Red
                            )

                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = cartItem.product.old_price.toString(),
                                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = cartItem.product.price.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )

                        } else {
                            Text(
                                text = cartItem.product.price.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
            if (favItem != null) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = favItem.product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            } else if (cartItem != null) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = cartItem.product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


