package com.seback.moviedbcompose.ui.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class SortOption(val name: String) : Parcelable {
    @Parcelize
    data object Alphabetical : SortOption("Alphabetical")

    @Parcelize
    data object Newest : SortOption("Newest")

    @Parcelize
    data object Popularity : SortOption("Popularity")

    @Parcelize
    data object Rating : SortOption("Rating")
}