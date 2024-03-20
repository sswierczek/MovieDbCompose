package com.seback.moviedbcompose.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seback.moviedbcompose.core.data.models.Genre
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.core.data.models.sortDataBy
import com.seback.moviedbcompose.discover.data.DiscoverOptions
import com.seback.moviedbcompose.discover.usecases.DiscoverNewMoviesUseCase
import com.seback.moviedbcompose.discover.usecases.GenresListUseCase
import com.seback.moviedbcompose.favs.data.FavMovieUseCase
import com.seback.moviedbcompose.ui.common.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val favUseCase: FavMovieUseCase,
    private val genresListUseCase: GenresListUseCase,
    private val discoverNewMoviesUseCase: DiscoverNewMoviesUseCase
) : ViewModel() {

    private val _favs: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val favs: StateFlow<List<Movie>> = _favs

    private val _result: MutableStateFlow<Response<List<Movie>>> =
        MutableStateFlow(Response.Success(emptyList()))
    val result: StateFlow<Response<List<Movie>>> = _result

    private val _genres: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    private val _sortOrder: MutableStateFlow<SortOption> = MutableStateFlow(SortOption.Rating)

    private var searchJob: Job? = null

    init {
        observeFavs()
        fetchGenres()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            genresListUseCase.execute()
                .flowOn(Dispatchers.IO)
                .collect {
                    _genres.value = it
                }
        }
    }


    fun favSwitch(movie: Movie) {
        viewModelScope.launch {
            favUseCase.favSwitch(movie)
        }
    }

    private fun observeFavs() {
        viewModelScope.launch {
            favUseCase.all()
                .flowOn(Dispatchers.IO)
                .collect {
                    _favs.value = it
                }
        }
    }

    fun retry() {
        TODO("Not yet implemented")
    }

    fun discoverBy(options: DiscoverOptions, sortOption: SortOption = _sortOrder.value) {
        searchJob = viewModelScope.launch {
            discoverNewMoviesUseCase.execute(options, sortOption)
                .flowOn(Dispatchers.IO)
                .collect {
                    _result.value = if (it is Response.Success) {
                        it.sortDataBy(sortOption = sortOption)
                    } else {
                        it
                    }
                }
        }
    }

    fun sortOrderChanged(options: DiscoverOptions, sortOption: SortOption) {
        _sortOrder.value = sortOption
        discoverBy(options, sortOption)
    }
}