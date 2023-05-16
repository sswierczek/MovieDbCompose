package com.seback.moviedbcompose.discover.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.discover.usecases.GetDiscoverMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscoverLatestViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel() {

    private val _result: MutableStateFlow<Response<List<Movie>>> =
        MutableStateFlow(Response.Loading(initialData = emptyList()))
    val result: StateFlow<Response<List<Movie>>> = _result

    init {
        fetchData()
    }

    fun retry() {
        fetchData()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }

    private fun fetchData() {
        viewModelScope.launch {
            getDiscoverMoviesUseCase.execute(1)
                .flowOn(Dispatchers.IO)
                .collect {
                    Timber.d("collect [${Thread.currentThread().name}]")
                    _result.value = it
                }
        }
    }
}