package com.seback.moviedbcompose.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.seback.moviedbcompose.R
import com.seback.moviedbcompose.core.data.Repository
import com.seback.moviedbcompose.ui.common.Tab
import com.seback.moviedbcompose.ui.common.Tabs
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme


@Composable
fun HomeTabs(onTab: (Repository.Home.HomeDataType) -> Unit) {
    val tabs = listOf(
        Tab(
            Repository.Home.HomeDataType.LATEST,
            stringResource(id = R.string.latest),
            Icons.Default.RocketLaunch
        ),
        Tab(
            Repository.Home.HomeDataType.POPULAR,
            stringResource(id = R.string.popular),
            Icons.Default.LocalFireDepartment
        ),
        Tab(
            Repository.Home.HomeDataType.TOP,
            stringResource(id = R.string.top),
            Icons.Default.Recommend
        )
    )
    Tabs(
        tabs = tabs,
        onTab = onTab
    )
}

@Preview
@Composable
fun HomeTabsPreview() {
    MovieDbComposeTheme {
        HomeTabs(onTab = {})
    }
}