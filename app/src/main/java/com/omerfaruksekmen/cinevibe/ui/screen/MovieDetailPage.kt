package com.omerfaruksekmen.cinevibe.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import com.omerfaruksekmen.cinevibe.R
import com.omerfaruksekmen.cinevibe.data.entity.Movies
import com.omerfaruksekmen.cinevibe.ui.viewmodel.FavoritesPageViewModel
import com.omerfaruksekmen.cinevibe.ui.viewmodel.MovieDetailPageViewModel
import com.skydoves.landscapist.glide.GlideImage
import android.widget.Toast

// Movie Detail Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailPage(incomingMovieObject: Movies, navController: NavController, movieDetailPageViewModel: MovieDetailPageViewModel, favoritesPageViewModel: FavoritesPageViewModel) {
    val favoriteStatus = favoritesPageViewModel.favoriteStatus.observeAsState(false)
    val context = LocalContext.current

    /* As soon as the page is opened, a query is made to the Room database through the viewmodel to
    check if the relevant movie exists. If the movie is found in the Room database, the favorite
    status is set to true, otherwise, it is set to false, and the add-remove favorite actions
    are managed. At the same time, the heart icon color is updated.*/
    LaunchedEffect(key1 = incomingMovieObject.name) {
        favoritesPageViewModel.favoriteControl(incomingMovieObject.name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center) },
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
                    IconButton(
                        onClick = {
                            // If it's in the favorites, it removes it.
                            if (favoriteStatus.value) {
                                favoritesPageViewModel.deleteFavorite(incomingMovieObject.name)
                                Toast.makeText(context,
                                    "${incomingMovieObject.name} Removed from favorites",
                                    Toast.LENGTH_SHORT).show()
                            } else {
                                // If it's not in the favorites, it adds it.
                                favoritesPageViewModel.addFavorite(incomingMovieObject)
                                Toast.makeText(context,
                                    "${incomingMovieObject.name} Added to favorites",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.favorite_icon),
                            contentDescription = "Favorite",
                            tint = if (favoriteStatus.value) Color.Red else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242A32)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF242A32))
                .padding(paddingValues)
        ) {
            // Movie poster and details section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                val activity = (context as Activity)
                /* Background images are fetched and displayed locally using the movie names received
                from the web service. A special condition was added for 12monkeys, as the image name
                could not be added starting with a number. */
                if(incomingMovieObject.image == "12monkeys.png"){
                    Image(painter = painterResource(id = R.drawable.monkeys12),
                        contentDescription = "", modifier = Modifier.height(220.dp),
                        contentScale = ContentScale.Crop)
                }else{
                    Image(bitmap = ImageBitmap.imageResource(id = activity.resources.getIdentifier(
                        incomingMovieObject.name.lowercase().replace(" ", ""),
                        "drawable", activity.packageName)),
                        modifier = Modifier.height(220.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .offset(y = 50.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Movie poster
                        Card(
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            GlideImage(
                                imageModel = "http://kasimadalan.pe.hu/movies/images/${incomingMovieObject.image}",
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Movie name and rating information
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.Bottom)
                        ) {
                            Text(
                                text = incomingMovieObject.name,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "${incomingMovieObject.rating}",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }

                    // Movie Details
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Year
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Year",
                                tint = Color(0xFF92929D),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${incomingMovieObject.year}",
                                color = Color(0xFF92929D),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        // Category
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.category_icon),
                                contentDescription = "Category",
                                tint = Color(0xFF92929D),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = incomingMovieObject.category,
                                color = Color(0xFF92929D),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        // Director
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(id = R.drawable.director_icon),
                                contentDescription = "Director",
                                tint = Color(0xFF92929D),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = incomingMovieObject.director,
                                color = Color(0xFF92929D),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // About the movie section
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "About the movie",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Text(
                    text = incomingMovieObject.description,
                    color = Color.White,
                    lineHeight = 20.sp,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // The section with price and quantity information, along with the add to cart button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF242A32))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Price information
                Column {
                    Text(
                        text = "Price",
                        color = Color(0xFF92929D),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$${incomingMovieObject.price}",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Quantity selection
                var quantity by remember { mutableStateOf(1) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .border(width = 2.dp, color = Color(0xFF0296E5), shape = RoundedCornerShape(8.dp) )
                ) {
                    // Decrease button
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "−",
                            color = if (quantity > 1) Color.White else Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Quantity information
                    Text(
                        text = quantity.toString(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Increase button
                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "+",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Add to cart button
                Button(
                    onClick = {
                        movieDetailPageViewModel.addMovieToCart(
                            incomingMovieObject.name, incomingMovieObject.image, incomingMovieObject.price,
                            incomingMovieObject.category, incomingMovieObject.rating, incomingMovieObject.year,
                            incomingMovieObject.director, incomingMovieObject.description,
                            quantity, "omerfaruk_sekmen"
                        )
                        Toast.makeText(context,
                            "${incomingMovieObject.name} added to cart (${quantity} quantity)",
                            Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0296E5)
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}