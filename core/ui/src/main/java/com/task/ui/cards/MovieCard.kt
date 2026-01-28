package com.task.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    modifier: Modifier = Modifier,
    onMovieClick: () -> Unit
) {


    Card(
        shape = CARD_SHAPE,
        elevation = CardDefaults.cardElevation(CARD_ELEVATION),
        modifier = modifier
            .width(300.dp)
            .clickable { onMovieClick() }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(LARGE_PADDING)
        ) {
            AsyncImage(
                model = posterPath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .aspectRatio(IMAGE_RATIO)
                    .clip(CARD_SHAPE)
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )


                Spacer(modifier = Modifier.width(SMALL_PADDING))


            }

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
                    val halfStar = (rating % 1 >= 0.5f)
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
    MovieBrowserTheme() {
        MovieCard(
            posterPath = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDI5LWFmNTEtODM1ZTI2ZDJmZjBhXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_FMjpg_UX1000_.jpg",
            title = "The Matrix",
            year = "1999",
            rating = 4.5f,
            onMovieClick = {}
        )
    }
}