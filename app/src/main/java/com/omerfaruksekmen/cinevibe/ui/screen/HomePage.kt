package com.omerfaruksekmen.cinevibe.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.omerfaruksekmen.cinevibe.ui.viewmodel.HomePageViewModel
import com.omerfaruksekmen.cinevibe.R

// Creating an enum class to define sorting options for filtering operations.
enum class SortingMethods(val title: String) {
    RATING_ASC("By Rating (ascending)"),
    RATING_DESC("By Rating (descending)"),
    YEAR_ASC("By Year (old to new)"),
    YEAR_DESC("By Year (new to old)"),
    PRICE_ASC("By Price (ascending)"),
    PRICE_DESC("By Price (descending)")
}

// Home Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel) {
    val movieList = homePageViewModel.movieList.observeAsState(listOf())
    val selectedSortingMethods = remember { mutableStateOf(SortingMethods.RATING_DESC) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "CineVibe", color = Color.White, fontWeight = FontWeight.Bold)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242A32)
                )
            )
        },
        bottomBar = {
            var selectedItem by remember { mutableStateOf(0) }

            NavigationBar(
                containerColor = Color(0xFF242A32),
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            "Home",
                            tint = if (selectedItem == 0) Color(0xFF0296E5) else Color(0xFF67686D),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            color = if (selectedItem == 0) Color(0xFF0296E5) else Color(0xFF67686D),
                            fontSize = 12.sp
                        )
                    },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        unselectedIconColor = Color(0xFF67686D),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.ShoppingCart,
                            "Cart",
                            tint = if (selectedItem == 1) Color(0xFF0296E5) else Color(0xFF67686D),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Cart",
                            color = if (selectedItem == 1) Color(0xFF0296E5) else Color(0xFF67686D),
                            fontSize = 12.sp
                        )
                    },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        navController.navigate("cartPage")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        unselectedIconColor = Color(0xFF67686D),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.favorite_icon),
                            "Favorites",
                            tint = if (selectedItem == 2) Color(0xFF0296E5) else Color(0xFF67686D)
                        )
                    },
                    label = {
                        Text(
                            "Favorites",
                            color = if (selectedItem == 2) Color(0xFF0296E5) else Color(0xFF67686D)
                        )
                    },
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        navController.navigate("favoritesPage")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        unselectedIconColor = Color(0xFF67686D),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.search_icon),
                            "Search",
                            tint = if (selectedItem == 3) Color(0xFF0296E5) else Color(0xFF67686D)
                        )
                    },
                    label = {
                        Text(
                            "Search",
                            color = if (selectedItem == 3) Color(0xFF0296E5) else Color(0xFF67686D)
                        )
                    },
                    selected = selectedItem == 3,
                    onClick = {
                        selectedItem = 3
                        navController.navigate("searchPage")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0296E5),
                        unselectedIconColor = Color(0xFF67686D),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF242A32))
        ) {
            // The area where the TOP 10 movies are listed based on their rating ranking.
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                val sortedMovies = movieList.value
                    .sortedByDescending { it.rating }
                    .take(10)

                items(
                    count = sortedMovies.count(),
                    itemContent = { index ->
                        val movie = sortedMovies[index]
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(8.dp)
                        ) {
                            Card(
                                modifier = Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                GlideImage(
                                    imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Text(
                                text = "${index + 1}",
                                color = Color.Yellow,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .offset(x = (-8).dp, y = 8.dp)
                                    .scale(1.2f)
                            )
                        }
                    }
                )
            }

            // The area where sorting options for filtering operations are located.
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                items(SortingMethods.values().size) { index ->
                    val sort = SortingMethods.values()[index]
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { selectedSortingMethods.value = sort },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = sort.title,
                            modifier = Modifier.padding(8.dp),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        if (selectedSortingMethods.value == sort) {
                            Divider(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(2.dp),
                                color = Color(0xFF0296E5)
                            )
                        }else{
                            Spacer(
                                modifier = Modifier.height(2.dp)
                            )
                        }
                    }
                }
            }

            // The area where the 3-column movie grid structure is located.
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(8.dp)
            ) {
                val sortedMovies = when (selectedSortingMethods.value) {
                    SortingMethods.RATING_ASC -> movieList.value.sortedBy { it.rating }
                    SortingMethods.RATING_DESC -> movieList.value.sortedByDescending { it.rating }
                    SortingMethods.YEAR_ASC -> movieList.value.sortedBy { it.year }
                    SortingMethods.YEAR_DESC -> movieList.value.sortedByDescending { it.year }
                    SortingMethods.PRICE_ASC -> movieList.value.sortedBy { it.price }
                    SortingMethods.PRICE_DESC -> movieList.value.sortedByDescending { it.price }
                }

                items(
                    count = sortedMovies.count(),
                    itemContent = {
                        val movie = sortedMovies[it]
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    val movieJson = Gson().toJson(movie)
                                    navController.navigate("movieDetailPage/${movieJson}")
                                }
                        ) {
                            Column {
                                GlideImage(
                                    imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}