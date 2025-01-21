package com.example.flickfinder.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerDetails(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)) { // Add padding to the whole column
            // Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(40.dp)
                        .shimmerEffect()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Information Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Spacing between texts
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier,
                //  verticalAlignment = Alignment.CenterVertically
            ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .height(200.dp)
                                    .shimmerEffect()

                            )


                }

            Spacer(modifier = Modifier.height(8.dp))




            Row(
                modifier = Modifier.padding(bottom = 2.dp),
                //  verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier) {


                    Row(
                    ) {
                        Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .shimmerEffect()

                            )
                        }
                    }
                }
            Row (){
                Column {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .height(160.dp)
                            .shimmerEffect()

                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(modifier = Modifier.padding(start = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .height(160.dp)
                            .shimmerEffect()

                    )
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    // Title Placeholder
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(20.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Cast Items Placeholder
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(5) { // Placeholder for 5 items
                            Column(
                                modifier = Modifier.width(100.dp)
                            ) {
                                // Image Placeholder
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .shimmerEffect()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                // Name Placeholder
                                Box(
                                    modifier = Modifier
                                        .height(16.dp)
                                        .fillMaxWidth(0.8f)
                                        .shimmerEffect()
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                // Role Placeholder
                                Box(
                                    modifier = Modifier
                                        .height(14.dp)
                                        .fillMaxWidth(0.6f)
                                        .shimmerEffect()
                                )
                            }
                        }
                    }
                }


            }

        }



    } else {
        contentAfterLoading()
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}