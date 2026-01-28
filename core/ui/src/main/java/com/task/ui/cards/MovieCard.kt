package com.task.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.task.designsystem.constants.Elevations.CARD_ELEVATION
import com.task.designsystem.constants.Paddings.LARGE_PADDING
import com.task.designsystem.constants.Paddings.MEDIUM_PADDING
import com.task.designsystem.constants.Paddings.SMALL_PADDING
import com.task.designsystem.constants.Ratio.IMAGE_RATIO
import com.task.designsystem.constants.Shapes.CARD_SHAPE
import com.task.designsystem.theme.MovieBrowserTheme

@Composable
fun MovieCard(
    posterPath: String,
    title: String,
    year: String,
    rating: Float,
    isSaved: Boolean,
    onMovieClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Card(
        shape = CARD_SHAPE,
        elevation = CardDefaults.cardElevation(CARD_ELEVATION),
        modifier = Modifier
            .width(300.dp)
            .clickable { onMovieClick() }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(LARGE_PADDING)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = posterPath,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .aspectRatio(IMAGE_RATIO)
                        .clip(CARD_SHAPE)
                )

                Icon(
                    imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Save movie",
                    tint = if (isSaved) Color.Red else Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(MEDIUM_PADDING)
                        .size(32.dp)
                        .clickable { onSaveClick() }
                )
            }

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(SMALL_PADDING))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = year,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val fullStars = rating.toInt()
                    val halfStar = rating % 1 >= 0.5f

                    repeat(fullStars) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (halfStar) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieCardPreview() {
    MovieBrowserTheme {
        MovieCard(
            posterPath = "https://image.tmdb.org/t/p/w300/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
            title = "The Matrix",
            year = "1999",
            rating = 4.5f,
            isSaved = true,
            onMovieClick = {},
            onSaveClick = {}
        )
    }
}