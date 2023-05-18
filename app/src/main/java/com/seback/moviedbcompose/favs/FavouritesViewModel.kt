package com.seback.moviedbcompose.favs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seback.moviedbcompose.core.data.models.Movie
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.favs.data.FavListUseCase
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
    private val favUseCase: FavListUseCase,
) : ViewModel() {

    private val _result: MutableStateFlow<Response<List<Movie>>> =
        MutableStateFlow(Response.Loading(initialData = emptyList()))
    val result: StateFlow<Response<List<Movie>>> = _result

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            favUseCase.execute()
                .flowOn(Dispatchers.IO)
                .collect {
                    _result.value = it
                }
        }
    }

    fun retry() {
        fetchData()
    }
}
