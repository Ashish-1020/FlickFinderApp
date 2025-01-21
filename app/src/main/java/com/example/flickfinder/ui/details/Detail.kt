package com.example.flickfinder.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flickfinder.R
import com.example.flickfinder.data.CastCrewResponse
import com.example.flickfinder.data.DetailsResponse
import com.example.flickfinder.ui.components.ShimmerDetails
import com.example.flickfinder.ui.components.YouTubePlayer
import com.example.flickfinder.ui.home.HomeViewModel

@Composable
fun Detail(viewModel: HomeViewModel, releaseId: Int) {
    val detail by viewModel.releaseDetails.collectAsState()
    val castAndCrew by viewModel.castAndCrew.collectAsState()
    val isLoading by viewModel.loadingdetail.collectAsState()

    if (detail == null) {
        viewModel.loadDetail(releaseId, "api")
    }
    if (castAndCrew.isEmpty()) {
        viewModel.loadCastAndCrew(releaseId, "api")
    }



    ShimmerDetails(
        isLoading = isLoading,
        contentAfterLoading = {
            detail?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent.copy(0.8f)),
                ) {
                    item {
                        detailheading(detail!!)
                    }
                    item {
                        Column {
                            Row {
                                YouTubePlayer(
                                    youtubeVideoUrl = detail!!.trailer ?: "",
                                    lifecycleOwner = LocalLifecycleOwner.current
                                )
                            }
                        }
                    }
                    item {
                        PosterBox(detail!!)
                    }
                    item {
                        Rating(detail!!)
                    }


                    item {
                        if (castAndCrew.isNotEmpty()) {
                            CastSection(castAndCrew)
                        }
                    }
                    item {
                        StreamingPlatforms(detail!!)
                    }


                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}






@Composable
fun CastSection(castList: List<CastCrewResponse>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Top Cast",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(castList) { cast ->
                CastItem(cast)
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun CastItem(cast: CastCrewResponse) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = cast.headshot_url,
            contentDescription = "Cast Image",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Text(
            text = cast.full_name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = cast.role,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun detailheading(detail:DetailsResponse) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)) { // Add padding to the whole column
        // Title Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "${detail.title}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        // Information Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Spacing between texts
        ) {
            Text(
                text = "${detail.type}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f) // Slightly faded color
                )
            )

            Text(
                text = "${detail.release_date}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                )
            )

            Text(
                text = "${detail.runtime_minutes/60}hrs  ${detail.runtime_minutes%60}min",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                )
            )
        }
    }
}



@Composable
fun PosterBox(detail: DetailsResponse) {
    var isExpanded by remember { mutableStateOf(false) } // State to track if the text is expanded

    Row(
        modifier = Modifier.padding(8.dp),
      //  verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) {
            val items = detail.genre_names

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.take(4).forEach { item ->
                    Box(
                        modifier = Modifier

                            .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                            .background(color = Color.White, RoundedCornerShape(12.dp))
                            .padding(8.dp)

                    ) {
                        Text(
                            text = item,
                            color = Color.Black.copy(0.7f),
                            style =MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
      }}

        Spacer(modifier = Modifier.width(8.dp))
Row (modifier = Modifier.padding(start = 8.dp)){
    Column {
        AsyncImage(
            model = detail.poster,
            contentDescription = detail.title,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxHeight()
        )
    }

        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = Modifier.padding(start = 8.dp)) {
            val maxLines =
                if (isExpanded) Int.MAX_VALUE else 8 // Set max lines based on expansion state
            Column(modifier = Modifier.padding(end = 8.dp)) {
                Text(
                    text = detail.plot_overview,
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis
                )

                if (detail.plot_overview.length > 200) { // Arbitrary threshold for long text
                    Text(
                        text = if (isExpanded) "Show Less" else "Show More",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.clickable { isExpanded = !isExpanded }
                    )
                }
            }
        }
    }

}




@Composable
fun Rating(detail: DetailsResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Ensures vertical center alignment
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating Star",
                tint = Color.Yellow
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${detail.user_rating ?: "N.A"}/10",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Critic Score: ${detail.critic_score ?: "N.A"}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Adds spacing between the content and divider

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = 8.dp), // Adds padding around the divider
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun StreamingPlatforms(detail: DetailsResponse) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Streaming Platforms",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        var isnull: Boolean = true

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                detail.sources.forEach { source ->
                    val drawableId = getPlatformDrawable(source.name)
                    println("${source.name}")
                    if (drawableId != null) {
                        isnull = false
                        // Display logo as an Image
                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = source.name,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    // Open the web URL
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(source.web_url))
                                    context.startActivity(intent)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }

            // Check if any drawableId was null and display the message
            if (isnull) {
                Spacer(modifier = Modifier.height(8.dp)) // Adds spacing above the text
                Text(
                    text = "⚠\uFE0F DETAIL ABOUT STREAMING PLATFORM NOT AVAILABLE on API !!",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium, // Optional: Adjust style
                    textAlign = TextAlign.Center // Center-align the text
                )
            }
        }


        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// Function to map platform names to their drawable resources
fun getPlatformDrawable(platformName: String): Int? {
    return when (platformName.lowercase()) {
        "prime video" -> R.drawable.prime_video
        "binge" -> R.drawable.binge
        "itunes" -> R.drawable.itunes
        "fubotv" -> R.drawable.fubo_tv
        "hulu" -> R.drawable.hulu
        "hbo max" -> R.drawable.hbo_max
        "netflix" -> R.drawable.netflix
        "appletv+"->R.drawable.icons8_apple_tv
        else -> null // Return null if no logo is found
    }
}
