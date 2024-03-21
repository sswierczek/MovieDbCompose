package com.seback.moviedbcompose.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seback.moviedbcompose.core.data.models.Genre
import com.seback.moviedbcompose.ui.common.ChipWithCloseIcon
import com.seback.moviedbcompose.ui.common.FilterButton
import com.seback.moviedbcompose.ui.theme.MovieDbComposeTheme
import java.util.Calendar

@Composable
fun MultiSelectDropdown(
    modifier: Modifier = Modifier,
    text: String,
    values: List<Genre>,
    selectedValues: List<Genre>,
    onSelectedValuesChange: (List<Genre>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedButton(onClick = { expanded = true }) {
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
                    Text(text = value.name, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
fun YearDropdown(
    modifier: Modifier = Modifier,
    text: String,
    range: IntRange,
    selectedYear: Int,
    onSelectedYearChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = "$text $selectedYear")
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
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiscoverFilterScreen(
    modifier: Modifier,
    genres: List<Genre>,
    selectedGenres: List<Genre>,
    onSelectedGenresChange: (List<Genre>) -> Unit,
    selectedStartYear: Int,
    onSelectedStartChange: (Int) -> Unit,
    selectedEndYear: Int,
    onSelectedEndYearChange: (Int) -> Unit,
    onDiscoverClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            selectedGenres.forEach { genre ->
                ChipWithCloseIcon(Modifier.padding(2.dp), text = genre.name) {
                    onSelectedGenresChange(selectedGenres - genre)
                }
            }
        }
        MultiSelectDropdown(
            text = if (selectedGenres.isEmpty()) "All genres" else "Select more genres",
            values = genres,
            selectedValues = selectedGenres,
            onSelectedValuesChange = onSelectedGenresChange,
        )
        Row {
            YearDropdown(
                modifier = Modifier.padding(end = 4.dp),
                text = "From ",
                range = 1900..Calendar.getInstance().get(Calendar.YEAR),
                selectedYear = selectedStartYear,
                onSelectedYearChange = onSelectedStartChange
            )
            YearDropdown(
                text = "to ",
                range = selectedStartYear..Calendar.getInstance().get(Calendar.YEAR),
                selectedYear = selectedEndYear,
                onSelectedYearChange = onSelectedEndYearChange,
            )
        }
        FilterButton(
            text = "Filter",
            onClick = onDiscoverClick,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DiscoverFiltersPreview() {
    MovieDbComposeTheme {
        DiscoverFilterScreen(
            Modifier.fillMaxSize(),
            genres = listOf(
                Genre(1, "Horror"),
                Genre(2, "Drama"),
                Genre(3, "Comedy")
            ),
            selectedGenres = emptyList(),
            onSelectedGenresChange = {},
            selectedStartYear = 2022,
            onSelectedStartChange = {},
            selectedEndYear = 2022,
            onSelectedEndYearChange = {},
            onDiscoverClick = {})
    }
}