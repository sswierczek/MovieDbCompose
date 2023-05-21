package com.seback.moviedbcompose.favs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.favs.data.FavMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val favMovieUseCase: FavMovieUseCase
) : ViewModel() {

    private val _result: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val result: StateFlow<List<Movie>> = _result

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            favMovieUseCase.all().flowOn(Dispatchers.IO).collect {
                _result.value = it
            }
        }
    }

    fun removeFav(movie: Movie) {
        viewModelScope.launch { favMovieUseCase.favSwitch(movie) }
    }
}
