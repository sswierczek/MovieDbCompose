package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class Tab<T>(
    val unique: T,
    val title: String,
    val icon: ImageVector
)

@Composable
fun <T> Tabs(tabs: List<Tab<T>>, onTab: (T) -> Unit) {
    var tabIndex by rememberSaveable { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, tab ->
                Tab(text = { Text(tab.title) },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                        onTab(tabs[tabIndex].unique)
                    },
                    icon = { Icon(imageVector = tab.icon, contentDescription = null) }
                )
            }
        }
    }
}