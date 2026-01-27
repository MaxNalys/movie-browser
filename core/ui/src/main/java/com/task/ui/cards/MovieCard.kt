package com.task.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
    posterUrl: String,
    title: String,
    overview: String,
    isSaved: Boolean,
    modifier: Modifier = Modifier,
    onMovieClick: () -> Unit,
    onSaveClick: () -> Unit
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
                model = posterUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .aspectRatio(IMAGE_RATIO)
                    .clip(CARD_SHAPE)
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(SMALL_PADDING))

                Icon(
                    imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Save movie",
                    tint = if (isSaved)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clickable { onSaveClick() }
                )
            }

            Spacer(modifier = Modifier.height(SMALL_PADDING))

            Text(
                text = overview,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(SMALL_PADDING))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    MovieBrowserTheme {
        MovieCard(
            posterUrl = "https://image.tmdb.org/t/p/w300/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
            title = "The Matrix",
            overview = "A computer hacker learns about the true nature of his reality.",
            isSaved = false,
            onMovieClick = {},
            onSaveClick = {}
        )
    }
}