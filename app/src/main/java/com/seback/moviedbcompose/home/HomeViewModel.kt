package com.seback.moviedbcompose.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.favs.data.FavMovieUseCase
import com.seback.moviedbcompose.home.paging.HomePagingSource
import com.seback.moviedbcompose.home.usecases.GetHomeLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val PAGE_SIZE = 5

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getLatest: GetHomeLatest,
    private val favUseCase: FavMovieUseCase,
    private val repository: Repository.Home
) : ViewModel() {

    val latest: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        HomePagingSource(Repository.Home.HomeDataType.LATEST, repository)
    }.flow.cachedIn(viewModelScope)

    val upcoming: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        HomePagingSource(Repository.Home.HomeDataType.UPCOMING, repository)
    }.flow.cachedIn(viewModelScope)

    val popular: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        HomePagingSource(Repository.Home.HomeDataType.POPULAR, repository)
    }.flow.cachedIn(viewModelScope)

    val top: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        HomePagingSource(Repository.Home.HomeDataType.TOP, repository)
    }.flow.cachedIn(viewModelScope)

    private val _latestMovies: MutableStateFlow<Response<List<Movie>>> =
        MutableStateFlow(Response.Loading(initialData = emptyList()))
    val latestMovies: StateFlow<Response<List<Movie>>> = _latestMovies

    private val _favs: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val favs: StateFlow<List<Movie>> = _favs

    init {
        observeLatest()
        observeFavs()
    }

    fun favSwitch(movie: Movie) {
        viewModelScope.launch {
            favUseCase.favSwitch(movie)
        }
    }

    private fun observeLatest() {
        viewModelScope.launch {
            getLatest.execute(1)
                .flowOn(Dispatchers.IO)
                .collect {
                    _latestMovies.value = it
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
}