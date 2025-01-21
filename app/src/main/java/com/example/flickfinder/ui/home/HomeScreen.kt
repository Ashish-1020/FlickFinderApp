package com.example.flickfinder.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.flickfinder.R

import com.example.flickfinder.data.Release
import com.example.flickfinder.ui.components.ShimmerDetails
import com.example.flickfinder.ui.components.ShimmerHome
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val releases by viewModel.releases.collectAsState()
    var showMovies by remember { mutableStateOf(true) }
    val isLoading by viewModel.loading.collectAsState()



    ShimmerHome(isLoading = isLoading, contentAfterLoading = {
    Scaffold(
        topBar = {
            // Top Bar with app name
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "FlickFinder",
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                actions = {
                    // Search Icon (can add an action here if needed)
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                // Toggle Buttons for Movies and TV Shows
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    // Movies Text with conditional color
                    Text(
                        text = "Movies",
                        color = if (showMovies) Color.Blue else Color.Gray,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .clickable { showMovies = true }
                            .padding(8.dp)
                    )

                    // Divider to separate the two options
                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                            .background(Color.White)
                    )

                    // TV Shows Text with conditional color
                    Text(
                        text = "TV Shows",
                        color = if (!showMovies) Color.Blue else Color.Gray,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .clickable { showMovies = false }
                            .padding(8.dp)
                    )
                }

                // Display Releases in a Grid Layout
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        releases.filter {
                            it.type == if (showMovies) "movie" else "tv_series"
                        }
                    ) { release ->
                        // Check if the poster URL is null
                        ReleaseCard(release = release, navController = navController,viewModel)
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    }
    )
}

@Composable
fun ReleaseCard(release: Release, navController: NavController,viewModel: HomeViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Navigate to DetailScreen
                viewModel.loadDetail(release.id, "api")
                viewModel.loadCastAndCrew(release.id, "api")
                navController.navigate("details/${release.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            if (release.poster_url.isNullOrEmpty()) {
                // Placeholder when poster_url is null or empty
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N.A",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.Bold)
                    )
                }
            } else {
                // Poster Image as Background
                Image(
                    painter = rememberImagePainter(data = release.poster_url),
                    contentDescription = release.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Transparent Overlay with Title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            ) {
                MarqueeText(
                    text = release.title,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun MarqueeText(
    text: String,
    textStyle: androidx.compose.ui.text.TextStyle,
    modifier: Modifier = Modifier// Speed in pixels per second
) {
    val scrollState = rememberScrollState()

    // Only animate if the text is longer than the screen width
    val isScrollable = remember { text.length > 1}  // You can adjust this threshold
    if (isScrollable) {
        LaunchedEffect(scrollState) {
            while (true) {
                scrollState.animateScrollTo(
                    scrollState.maxValue,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 4000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                scrollState.scrollTo(0) // Reset to the start
            }
        }
    }

    Row(
        modifier = modifier
            .horizontalScroll(scrollState, false)
            .padding(8.dp)
    ) {
        Text(text = text, style = textStyle)
    }
}
