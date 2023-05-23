package com.seback.moviedbcompose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.seback.moviedbcompose.R

sealed class BottomNavigationItem(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavigationItem("home", R.string.home, Icons.Default.Home)

    object Discover : BottomNavigationItem("discover", R.string.discover, Icons.Default.Movie)
    object Favourites : BottomNavigationItem("favs", R.string.favs, Icons.Default.Favorite)
}