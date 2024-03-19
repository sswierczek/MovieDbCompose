package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seback.moviedbcompose.R
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import kotlinx.datetime.LocalDate

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    isFav: Boolean,
    onFavClick: (Movie) -> Unit
) {

    Card(
        modifier = modifier
            .padding(8.dp, 8.dp)
            .fillMaxWidth()
            .aspectRatio(0.56f) // 9:16
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.sizeIn(minWidth = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                AsyncImage(
//                    modifier = Modifier.size(10.dp), // PREVIEW
                    model = movie.posterPath,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.FillWidth,
                )
            }
            Spacer(Modifier.weight(1f))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Rating(
                    modifier = Modifier.padding(start = 8.dp),
                    vote = movie.voteAverage
                )
                Spacer(modifier = Modifier.weight(1f))
                FavouriteButton(movie = movie, isFav = isFav, onFavClick = onFavClick)
            }
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MovieDbComposeTheme {
        MovieCard(
            movie = Movie(
                id = 0,
                title = "Some Movie",
                backdropPath = "",
                posterPath = "",
                voteAverage = 8.9,
                releaseDate = LocalDate.parse("2021-01-01")
            ),
            isFav = true,
            onFavClick = {}
        )
    }
}