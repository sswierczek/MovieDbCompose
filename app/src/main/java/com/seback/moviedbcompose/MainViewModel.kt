package com.seback.moviedbcompose

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seback.moviedbcompose.core.data.models.Response
import com.seback.moviedbcompose.discover.usecases.GetDiscoverMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel() {


    fun fetch() {
        viewModelScope.launch {
            when (val response = getDiscoverMoviesUseCase.execute(1)) {
                is Response.Success -> {
                    Log.d("VMD", response.data.toString())
                }
                is Response.Error -> {
                    Log.e("VMD", response.message ?: "empty error")
                }
            }
        }
    }

    init {
        Log.d("VMD", "INIT")
    }


    override fun onCleared() {
        super.onCleared()

        Log.d("VMD", "onCleared")
    }
}