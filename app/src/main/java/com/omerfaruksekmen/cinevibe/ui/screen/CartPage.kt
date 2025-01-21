package com.omerfaruksekmen.cinevibe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.omerfaruksekmen.cinevibe.R
import com.omerfaruksekmen.cinevibe.ui.viewmodel.CartPageViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

// Cart Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(navController: NavController, cartPageViewModel: CartPageViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val movieListInCart = cartPageViewModel.movieListInCart.observeAsState(listOf())
    val isEmptyCart = cartPageViewModel.isEmptyCart.observeAsState(true)

    /* When the page is opened, the username information is sent to the ViewModel,
    and the movie information in the cart for the specified username is retrieved as a result of
    the request sent to the web service. */
    LaunchedEffect(key1 = true) {
        cartPageViewModel.getAllMoviesInTheCart("Enter your username here.")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painterResource(id = R.drawable.back_icon),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painterResource(id = R.drawable.cart_icon),
                            contentDescription = "Cart",
                            tint = Color(0xFF0296E5)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242A32)
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        // A check is made to see if the cart is empty, and if it is, a text is displayed on the screen.
        // If the cart is not empty, the movies in the cart returned from the web service are listed.
        if (isEmptyCart.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF242A32)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(modifier = Modifier.padding(16.dp),
                    text = "Your cart is empty. Please visit the homepage to purchase new movies. :)",
                    fontSize = 20.sp, lineHeight = 30.sp, color = Color.White,
                    textAlign = TextAlign.Center)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF242A32))
                    .padding(paddingValues)
            ) {
                // List of movies in the cart.
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(
                        count = movieListInCart.value.count(),
                        itemContent = {
                            val cartMovie = movieListInCart.value[it]
                            val movieTotalPrice = cartMovie.price * cartMovie.orderAmount
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .border(
                                        width = 1.dp, color = Color(0xFF0296E5),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF242A32)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Movie image
                                    GlideImage(
                                        imageModel = "http://kasimadalan.pe.hu/movies/images/${cartMovie.image}",
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    // Movie name and price
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            text = cartMovie.name,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Price: $${cartMovie.price}",
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Quantity: ${cartMovie.orderAmount}",
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                    }

                                    // Delete icon
                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ){
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    val snackbar = snackbarHostState.showSnackbar(
                                                        message = "Should ${cartMovie.name} be deleted?",
                                                        actionLabel = "YES"
                                                    )
                                                    if(snackbar == SnackbarResult.ActionPerformed){
                                                        cartPageViewModel.removeMovieFromCart(cartMovie.cartId, "Enter your username here.")
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.delete_icon),
                                                contentDescription = "Delete",
                                                tint = Color.Red
                                            )
                                        }
                                        Text(
                                            text = "$${movieTotalPrice}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )

                                    }

                                }
                            }
                        }
                    )
                }

                // Movie details and confirm cart button
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF242A32))
                        .padding(16.dp)
                ) {
                    // We multiply the quantity by the price and sum them up.
                    val totalPrice = movieListInCart.value.sumOf { it.price * it.orderAmount }
                    // If the total price of the items in the cart is 100 $ or below, a shipping fee of 20 $ is added.
                    val shippingPrice = if (totalPrice <= 100) 20.0 else 0.0
                    val grandTotal = totalPrice + shippingPrice

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Sub Total", color = Color.White)
                        Text(text = "$$totalPrice", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Shipping Price", color = Color.White)
                        Text(text = "$$shippingPrice", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.LightGray
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Grand Total", fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "$$grandTotal", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0296E5)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "CONFIRM CART",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}