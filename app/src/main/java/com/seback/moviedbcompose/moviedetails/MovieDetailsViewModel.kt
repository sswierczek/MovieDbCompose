package com.seback.moviedbcompose.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seback.moviedbcompose.core.data.models.MovieDetails
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.favs.data.FavMovieUseCase
import com.seback.moviedbcompose.moviedetails.usecases.GetMovieDetailsUseCase
import com.seback.moviedbcompose.navigation.ARGUMENT_MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUseCase: GetMovieDetailsUseCase,
    private val favMovieUseCase: FavMovieUseCase
) : ViewModel() {
    private val _result: MutableStateFlow<Response<MovieDetails>> =
        MutableStateFlow(Response.Loading(initialData = MovieDetails()))
    val result: StateFlow<Response<MovieDetails>> = _result

    private val _isFav: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFav: StateFlow<Boolean> = _isFav

    private val movieId = checkNotNull(savedStateHandle.get<Int>(ARGUMENT_MOVIE_ID))

    init {
        fetch()
    }

    fun retry() {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            getUseCase.execute(movieId)
                .flowOn(Dispatchers.IO)
                .collect {
                    _result.value = it
                }

            _isFav.value = favMovieUseCase.isFav(movieId)
        }
    }

    fun onFavClicked() {
        (result.value as? Response.Success<MovieDetails>)?.data?.let { movieDetails ->
            viewModelScope.launch {
                favMovieUseCase.favSwitch(movieDetails.toMovie())
                _isFav.value = favMovieUseCase.isFav(movieId)
            }
        }
    }
}