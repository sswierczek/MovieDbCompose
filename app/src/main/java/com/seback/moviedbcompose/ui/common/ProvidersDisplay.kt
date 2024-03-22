package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.MovieProvider
import com.seback.moviedbcompose.core.data.models.MovieProviderType


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProvidersDisplay(modifier: Modifier, movieDetails: MovieDetails) {

    Column(modifier = modifier.padding(16.dp)) {

        if (movieDetails.providers.isEmpty()) {
            Text(
                modifier = Modifier
                    .paddingFromBaseline(bottom = 16.dp),
                text = "Not available to rent or buy in Poland",
                style = MaterialTheme.typography.subtitle1
            )
        } else {
            val flatrate = movieDetails.providers.filter { it.type == MovieProviderType.FLATRATE }
            if (flatrate.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .paddingFromBaseline(top = 16.dp),
                    text = "Subscription:"
                )
                FlowRow {
                    for (provider in flatrate) {
                        ProviderLogo(provider)
                    }
                }
            }
            val toBuyOrRent =
                movieDetails.providers.filter { it.type == MovieProviderType.BUY || it.type == MovieProviderType.RENT }
                    .distinctBy { it.id }
            if (toBuyOrRent.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .paddingFromBaseline(top = 16.dp),
                    text = "To buy or rent:"
                )

                FlowRow {
                    for (provider in toBuyOrRent) {
                        ProviderLogo(provider)
                    }
                }
            }
        }
    }
}

@Composable
fun ProviderLogo(provider: MovieProvider) {
    AsyncImage(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = provider.logoPath,
        contentDescription = provider.name
    )
}
