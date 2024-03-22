package com.seback.moviedbcompose.core.data.models

import android.os.Parcelable
import com.seback.moviedbcompose.core.data.api.ApiRegion
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieRegion(
    val iso31661: String,
    val englishName: String,
    val nativeName: String
) : Parcelable

fun ApiRegion.map() = MovieRegion(
    iso31661 = iso_3166_1,
    englishName = english_name,
    nativeName = native_name
)