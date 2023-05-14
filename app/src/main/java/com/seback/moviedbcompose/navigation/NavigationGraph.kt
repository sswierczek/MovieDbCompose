package com.seback.moviedbcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seback.moviedbcompose.discover.ui.DiscoverLatestScreen
import com.seback.moviedbcompose.discover.ui.DiscoverNewMoviesScreen
import com.seback.moviedbcompose.favs.ui.FavouritesScreen
import com.seback.moviedbcompose.movies.ui.MovieDetailsScreen

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Latest.route
    ) {
        composable(BottomNavigationItem.Latest.route) {
            DiscoverLatestScreen(modifier, onMovieDetails = {
                navController.navigate("movieDetails/${it.id}")
            })
        }
        composable(BottomNavigationItem.Discover.route) {
            DiscoverNewMoviesScreen(modifier)
        }
        composable(BottomNavigationItem.Favourites.route) {
            FavouritesScreen(modifier)
        }
        composable(
            "movieDetails/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            MovieDetailsScreen(
                modifier = modifier,
                movieId = backStackEntry.arguments?.getInt("movieId")!!,
                onBack = { navController.popBackStack() }
            )
        }
    }
}