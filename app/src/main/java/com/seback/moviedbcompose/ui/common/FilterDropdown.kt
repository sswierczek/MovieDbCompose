package com.seback.moviedbcompose.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun FilterDropdown(text: String, values: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = { },
            placeholder = { Text(text = text) },
            enabled = false,
            modifier = Modifier
                .clickable {
                    expanded = true
                }
                .fillMaxWidth(0.8f),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            values.forEach {
                DropdownMenuItem(
                    onClick = {
                        selected = it
                        expanded = false
                        onSelected(selected)
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(it)
                }
            }
        }
    }
}