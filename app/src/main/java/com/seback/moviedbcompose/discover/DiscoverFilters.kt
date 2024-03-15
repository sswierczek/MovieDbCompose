package com.seback.moviedbcompose.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import java.util.Calendar


@Composable
fun MultiSelectDropdown(
    text: String,
    values: List<String>,
    selectedValues: List<String>,
    onSelectedValuesChange: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Button(onClick = { expanded = true }) {
        Text(text = text)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        values.forEach { value ->
            val isSelected = selectedValues.contains(value)
            DropdownMenuItem(onClick = {
                if (isSelected) {
                    onSelectedValuesChange(selectedValues - value)
                } else {
                    onSelectedValuesChange(selectedValues + value)
                }
            }) {
                Checkbox(checked = isSelected, onCheckedChange = null)
                Text(text = value, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun YearDropdown(
    text: String,
    range: IntRange,
    selectedYear: Int,
    onSelectedYearChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Button(onClick = { expanded = true }) {
        Text(text = "$text: $selectedYear")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        range.forEach { year ->
            DropdownMenuItem(onClick = {
                expanded = false
                onSelectedYearChange(year)
            }) {
                Text(text = year.toString())
            }
        }
    }
}

@Composable
fun SortDropdown(
    text: String,
    values: List<String>,
    selectedValue: String,
    onSelectedValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Button(onClick = { expanded = true }) {
        Text(text = "$text: $selectedValue")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        values.forEach { value ->
            DropdownMenuItem(onClick = {
                expanded = false
                onSelectedValueChange(value)
            }) {
                Text(text = value)
            }
        }
    }
}

@Composable
fun DiscoverFilters(
    modifier: Modifier,
    genres: List<String>,
    selectedGenres: List<String>,
    onSelectedGenresChange: (List<String>) -> Unit,
    selectedSortOrder: String,
    onSelectedSortOrderChange: (String) -> Unit,
    selectedStartYear: Int,
    onSelectedStartChange: (Int) -> Unit,
    selectedEndYear: Int,
    onSelectedEndYearChange: (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MultiSelectDropdown(
            text = "Genres",
            values = genres,
            selectedValues = selectedGenres,
            onSelectedValuesChange = onSelectedGenresChange
        )
        YearDropdown(
            text = "Start Year",
            range = 1900..Calendar.getInstance().get(Calendar.YEAR),
            selectedYear = selectedStartYear,
            onSelectedYearChange = onSelectedStartChange
        )

        YearDropdown(
            text = "End Year",
            range = selectedStartYear..Calendar.getInstance().get(Calendar.YEAR),
            selectedYear = selectedEndYear,
            onSelectedYearChange = onSelectedEndYearChange
        )
        SortDropdown(
            text = "Sort by",
            values = listOf("Rating", "Alphabetical", "Reverse Alphabetical"),
            selectedValue = selectedSortOrder,
            onSelectedValueChange = onSelectedSortOrderChange
        )
    }
}

@Composable
fun DiscoverFilterScreen(modifier: Modifier, genres: List<String>) {
    val selectedGenres = remember { mutableStateOf(emptyList<String>()) }
    val selectedStartYear = remember { mutableStateOf(2022) }
    val selectedEndYear = remember { mutableStateOf(2022) }
    val selectedSortOrder = remember { mutableStateOf("Rating") }
    DiscoverFilters(
        modifier = modifier,
        selectedGenres = selectedGenres.value,
        genres = genres,
        onSelectedGenresChange = { newGenres -> selectedGenres.value = newGenres },
        selectedStartYear = selectedStartYear.value,
        onSelectedStartChange = { newStart -> selectedStartYear.value = newStart },
        selectedEndYear = selectedEndYear.value,
        onSelectedEndYearChange = { newEnd -> selectedEndYear.value = newEnd },
        selectedSortOrder = selectedSortOrder.value,
        onSelectedSortOrderChange = { newOrder -> selectedSortOrder.value = newOrder }
    )
}

@Composable
@Preview(showBackground = true)
fun DiscoverFiltersPreview() {
    MovieDbComposeTheme {
        DiscoverFilterScreen(Modifier.fillMaxSize(), listOf("Horror", "Drama", "Comedy"))
    }
}