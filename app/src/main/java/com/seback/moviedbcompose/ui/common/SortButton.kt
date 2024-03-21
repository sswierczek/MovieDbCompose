package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.ui.data.SortOption
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme

@Composable
fun SortButton(
    modifier: Modifier,
    text: String,
    selectedSortOrder: SortOption,
    onSelectedSortOrderChange: (SortOption) -> Unit,
    sortOptions: List<SortOption>
) {
    SortDropdown(
        modifier = modifier,
        text = text,
        values = sortOptions,
        selectedValue = selectedSortOrder,
        onSelectedValueChange = onSelectedSortOrderChange
    )
}

@Composable
private fun SortDropdown(
    text: String,
    values: List<SortOption>,
    selectedValue: SortOption,
    onSelectedValueChange: (SortOption) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedButton(onClick = { expanded = true }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = selectedValue.name, modifier = Modifier.padding(end = 2.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Sort,
                    contentDescription = text
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.align(Alignment.Center)
        ) {
            values.forEach { value ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSelectedValueChange(value)
                }) {
                    Text(text = value.name)
                }
            }
        }
    }
}

@Preview
@Composable
fun SortButtonPreview() {
    MovieDbComposeTheme {
        SortButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sort by",
            selectedSortOrder = SortOption.Rating,
            onSelectedSortOrderChange = {},
            sortOptions = listOf(
                SortOption.Alphabetical,
                SortOption.Newest,
                SortOption.Popularity,
                SortOption.Rating
            )
        )
    }
}