package com.seback.moviedbcompose.discover.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.api.ApiSortBy
import com.seback.moviedbcompose.core.data.models.Genre
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.map
import com.seback.moviedbcompose.core.data.models.unknownError
import com.seback.moviedbcompose.core.network.NetworkConfig
import com.seback.moviedbcompose.ui.common.SortOption
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class DiscoverRepository @Inject constructor(
    retrofit: Retrofit,
    private val networkConfig: NetworkConfig
) : Repository.Discover {

    private val service = retrofit.create(RetrofitDiscoverService::class.java)

    override fun discover(
        page: Int,
        options: DiscoverOptions?,
        sortOption: SortOption
    ): Flow<Response<List<Movie>>> = flow {
        Timber.d("discover [${Thread.currentThread().name}]")

        when (val response =
            service.discoverMovies(
                page = page,
                sortBy = sortOption.toApiSortBy().value,
                genres = options?.selectedGenres?.map { it.id }?.joinToString(separator = "|"),
                yearStart = options?.selectedStartYear.yearToDate(),
                yearEnd = if (options?.selectedStartYear == options?.selectedEndYear) null else options?.selectedEndYear.yearToDate(),
                apiKey = networkConfig.apiKey
            )) {
            is NetworkResponse.Success -> {
                emit(Response.Success(response.body.results.map { it.map() }))
            }

            is NetworkResponse.Error -> {
                emit(Response.Error(response.error.message, response.error))
            }

            else -> {
                emit(unknownError())
            }
        }
    }

    override fun genres(): Flow<Response<List<Genre>>> = flow {
        Timber.d("genres [${Thread.currentThread().name}]")
        when (val response =
            service.genres(apiKey = networkConfig.apiKey)) {
            is NetworkResponse.Success -> {
                emit(Response.Success((response.body.genres ?: emptyList()).map { Genre(it.id, it.name) }))
            }

            is NetworkResponse.Error -> {
                emit(Response.Error(response.error.message, response.error))
            }

            else -> {
                emit(unknownError())
            }
        }
    }
}

private fun Int?.yearToDate(): String {
    return LocalDate(this ?: 1990, 1, 1).toString()
}

private fun SortOption.toApiSortBy(): ApiSortBy {
    return when (this) {
        SortOption.Alphabetical -> ApiSortBy.ORIGINAL_TITLE_ASC
        SortOption.Popularity -> ApiSortBy.POPULARITY_DESC
        SortOption.Newest -> ApiSortBy.RELEASE_DATE_DESC
        SortOption.Rating -> ApiSortBy.VOTE_COUNT_DESC
    }
}
