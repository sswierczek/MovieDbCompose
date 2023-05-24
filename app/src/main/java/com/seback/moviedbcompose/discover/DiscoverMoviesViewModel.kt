package com.seback.moviedbcompose.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscoverMoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    init {
        observeSearch()
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .filter { it.isNotEmpty() }
                .collect {
                    Timber.d("Search for $it")
                }
        }
    }

    fun searchTextChanged(value: String) {
        _searchQuery.value = value
    }
}