package com.seback.moviedbcompose.moviedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seback.moviedbcompose.R
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.thirdparty.youtube.YouTubePlayer
import com.seback.moviedbcompose.ui.common.ClickableUrl
import com.seback.moviedbcompose.ui.common.FavouriteButton
import com.seback.moviedbcompose.ui.common.ProvidersDisplay
import com.seback.moviedbcompose.ui.common.Rating

@Composable
fun MovieDetails(
    modifier: Modifier,
    movieDetails: MovieDetails,
    isFav: Boolean,
    onFavClick: (Movie) -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.77f), // 16:9
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            model = movieDetails.backdropPath ?: movieDetails.posterPath, contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .paddingFromBaseline(top = 32.dp, bottom = 16.dp),
            text = movieDetails.title,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Visible,
            maxLines = 1
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "${movieDetails.releaseDate.year}",
                style = MaterialTheme.typography.subtitle1
            )
            Rating(
                modifier = Modifier.padding(start = 16.dp),
                vote = movieDetails.vote
            )
            Spacer(modifier = Modifier.weight(1f))
            FavouriteButton(
                modifier = Modifier.size(32.dp),
                movie = movieDetails.toMovie(),
                isFav = isFav,
                onFavClick = onFavClick
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .paddingFromBaseline(bottom = 16.dp),
            text = movieDetails.overview,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body1
        )
        if (movieDetails.moreInfoUrl.isNotEmpty()) {
            ClickableUrl(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                text = "More info",
                url = movieDetails.moreInfoUrl
            )
        }
        ProvidersDisplay(
            modifier = Modifier,
            movieDetails = movieDetails
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ) {
            for (trailerId in movieDetails.youTubeVideosIds) {
                YouTubePlayer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    videoId = trailerId
                )
            }
        }
    }
}
