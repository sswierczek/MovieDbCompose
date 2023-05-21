package com.seback.moviedbcompose.latest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.discover.paging.DiscoverLatestPagingSource
import com.seback.moviedbcompose.discover.usecases.GetDiscoverMoviesUseCase
import com.seback.moviedbcompose.favs.data.FavMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverLatestViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUseCase: GetDiscoverMoviesUseCase,
    private val favUseCase: FavMovieUseCase,
    private val discoverPagerSource: DiscoverLatestPagingSource
) : ViewModel() {

    val moviesPager: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        discoverPagerSource
    }.flow.cachedIn(viewModelScope)

    private val _result: MutableStateFlow<Response<List<Movie>>> =
        MutableStateFlow(Response.Loading(initialData = emptyList()))
    val result: StateFlow<Response<List<Movie>>> = _result

    private val _favs: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val favs: StateFlow<List<Movie>> = _favs

    init {
        observeMovies()
        observeFavs()
    }

    private fun observeMovies() {
        viewModelScope.launch {
            getUseCase.execute(1)
                .flowOn(Dispatchers.IO)
                .collect {
                    _result.value = it
                }
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

    fun favSwitch(movie: Movie) {
        viewModelScope.launch {
            favUseCase.favSwitch(movie)
        }
    }
}