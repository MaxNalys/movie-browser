package com.task.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.task.designsystem.constants.Elevations
import com.task.designsystem.constants.Paddings.LARGE_PADDING
import com.task.designsystem.constants.Paddings.MEDIUM_PADDING
import com.task.designsystem.constants.Paddings.SMALL_PADDING
import com.task.designsystem.constants.Paddings.TINY_PADDING
import com.task.designsystem.constants.Ratio
import com.task.designsystem.constants.Shapes.CARD_SHAPE
import com.task.designsystem.theme.MovieBrowserTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder


@Composable
fun MovieDetailCard(
    posterPath: String,
    title: String,
    releaseDate: String,
    genres: List<String>,
    overview: String,
    rating: Float,
    voteCount: Int,
    isSaved: Boolean,
    onCardClick: () -> Unit,
    onSaveClick: () -> Unit
) {


    Card(
        shape = CARD_SHAPE,
        elevation = CardDefaults.cardElevation(defaultElevation = Elevations.CARD_ELEVATION),
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(LARGE_PADDING)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = posterPath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(Ratio.IMAGE_RATIO)
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(SMALL_PADDING))

                Text(
                    text = releaseDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(SMALL_PADDING))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(genres.size) { index ->
                    val genre = genres[index]
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = CARD_SHAPE
                            )
                            .padding(horizontal = SMALL_PADDING, vertical = TINY_PADDING)
                    )
                }
            }

            Spacer(modifier = Modifier.height(SMALL_PADDING))

            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Text(
                text = "($voteCount votes)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailCardPreview() {
    MovieBrowserTheme() {
        MovieDetailCard(
            posterPath = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDI5LWFmNTEtODM1ZTI2ZDJmZjBhXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_FMjpg_UX1000_.jpg",
            title = "Scream VI",
            releaseDate = "2023",
            genres = listOf("Horror", "Thriller"),
            overview = "Following the latest Ghostface killings, the four survivors leave Woodsboro behind and start a fresh chapter.",
            rating = 7.374f,
            isSaved = true,
            voteCount = 684,
            onCardClick = {},
            onSaveClick = {}
        )
    }
}
