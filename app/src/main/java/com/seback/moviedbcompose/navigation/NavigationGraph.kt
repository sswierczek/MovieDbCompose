package com.seback.moviedbcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seback.moviedbcompose.discover.DiscoverMoviesScreen
import com.seback.moviedbcompose.favs.FavouritesScreen
import com.seback.moviedbcompose.home.HomeScreen
import com.seback.moviedbcompose.moviedetails.MovieDetailsScreen
import com.seback.moviedbcompose.search.SearchScreen

const val ARGUMENT_MOVIE_ID = "movieId"
const val ARGUMENT_MOVIE_DETAILS = "movieDetails"

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.route
    ) {
        composable(BottomNavigationItem.Home.route) {
            HomeScreen(modifier, onMovieDetails = {
                navController.navigate("$ARGUMENT_MOVIE_DETAILS/${it.id}")
            })
        }
        composable(BottomNavigationItem.Search.route) {
            SearchScreen(modifier, onMovieDetails = {
                navController.navigate("$ARGUMENT_MOVIE_DETAILS/${it.id}")
            })
        }
        composable(BottomNavigationItem.Discover.route) {
            DiscoverMoviesScreen(modifier, onMovieDetails = {
                navController.navigate("$ARGUMENT_MOVIE_DETAILS/${it.id}")
            })
        }
        composable(BottomNavigationItem.Favourites.route) {
            FavouritesScreen(modifier, onMovieDetails = {
                navController.navigate("$ARGUMENT_MOVIE_DETAILS/${it.id}")
            })
        }
        composable(
            "movieDetails/{$ARGUMENT_MOVIE_ID}",
            arguments = listOf(navArgument(ARGUMENT_MOVIE_ID) { type = NavType.IntType })
        ) {
            MovieDetailsScreen(
                modifier = modifier,
                onBack = { navController.popBackStack() }
            )
        }
    }
}