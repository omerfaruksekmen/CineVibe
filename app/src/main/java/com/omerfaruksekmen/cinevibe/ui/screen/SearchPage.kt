package com.omerfaruksekmen.cinevibe.ui.screen

import androidx.compose.foundation.clickable
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
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.omerfaruksekmen.cinevibe.R
import com.omerfaruksekmen.cinevibe.ui.viewmodel.SearchPageViewModel

// Search Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(navController: NavController, searchPageViewModel: SearchPageViewModel) {
    val movieList = searchPageViewModel.movieList.observeAsState(listOf())
    val searchWord = searchPageViewModel.searchWord.collectAsState()

    /* As soon as the page is opened, all movies are fetched from the web service and relevant movies
    are listed according to the searched word.*/
    LaunchedEffect(key1 = true) {
        searchPageViewModel.getAllMovies()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchWord.value,
                        onValueChange = { searchPageViewModel.searchMovie(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        placeholder = { Text("Search Movie", color = Color(0xFF67686D)) },
                        leadingIcon = {
                            Icon(
                                painterResource(id = R.drawable.search_icon),
                                contentDescription = "Search",
                                tint = Color(0xFF67686D)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFF3A3F47),
                            cursorColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp)
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242A32)
                )
            )
        }
    ) { paddingValues ->
        if (searchWord.value.isEmpty()) {
            // When no search is made, a message is shown on a blank page..
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF242A32))
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Use the search box above to search for movies.",
                    fontSize = 20.sp, lineHeight = 30.sp, color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            // When searched, filtered movies are listed.
            val moviesWithFilter = movieList.value.filter {
                it.name.contains(searchWord.value, ignoreCase = true)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF242A32))
                    .padding(paddingValues)
            ) {
                items(
                    count = moviesWithFilter.size,
                    itemContent = { index ->
                        val movie = moviesWithFilter[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    val movieJson = Gson().toJson(movie)
                                    navController.navigate("movieDetailPage/${movieJson}")
                                },
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
                                // Movie Image
                                GlideImage(
                                    imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                // Movie Information
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 16.dp)
                                ) {
                                    Text(
                                        text = movie.name,
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
                                            text = "${movie.rating}",
                                            color = Color.White,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                    }

                                    Text(
                                        text = "${movie.year} | ${movie.director}",
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