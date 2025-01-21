package com.omerfaruksekmen.cinevibe.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.omerfaruksekmen.cinevibe.R
import com.omerfaruksekmen.cinevibe.ui.viewmodel.FavoritesPageViewModel

// Favorites Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesPage(navController: NavController, favoritesPageViewModel: FavoritesPageViewModel) {
    val favoritesList = favoritesPageViewModel.favoritesList.observeAsState(listOf())

    // As soon as the page is opened, it fetches all the favorites by going to the viewmodel.
    LaunchedEffect(key1 = true) {
        favoritesPageViewModel.getAllFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Favorites",
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
                            painterResource(id = R.drawable.favorite_icon),
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242A32)
                )
            )
        }
    ) { paddingValues ->
        if(favoritesList.value.isNullOrEmpty()){
            // If the favorite list is empty, an informational message is displayed."
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF242A32))
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "The favorite list appears to be empty. To add favorites, please visit the movie detail pages.",
                    fontSize = 20.sp, lineHeight = 30.sp, color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }else{
            // Using the LazyColumn structure, the favorite movies fetched from the Room database are listed.
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF242A32))
                    .padding(paddingValues)
            ) {
                items(
                    count = favoritesList.value.count(),
                    itemContent = {
                        val favoriteMovie = favoritesList.value[it]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF3A3F47)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GlideImage(
                                    imageModel = "http://kasimadalan.pe.hu/movies/images/${favoriteMovie.image}",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 16.dp)
                                ) {
                                    Text(
                                        text = favoriteMovie.name,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = "Rating",
                                            tint = Color(0xFFFFD700),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "${favoriteMovie.rating}",
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                    }

                                    Text(
                                        text = "${favoriteMovie.year} | ${favoriteMovie.director}",
                                        color = Color(0xFF92929D),
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
} 