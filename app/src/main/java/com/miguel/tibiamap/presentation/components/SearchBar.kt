package com.miguel.tibiamap.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchBar(modifier: Modifier = Modifier, isVisibleMap: MutableState<Boolean>){
    var searchQuery = remember { mutableStateOf("") }
    val items = listOf(
        "Apple",
        "Banana",
        "Cherry",
        "Date",
        "Elderberry",
        "Fig",
        "Grape",
        "Honeydew"
    )

    var active = remember { mutableStateOf(false) }
    val filteredItems =
        items.filter { it.contains(searchQuery.value, ignoreCase = true) }

    SearchBar(
        query = searchQuery.value,
        onQueryChange = { searchQuery.value = it },
        onSearch = { active.value = false },
        active = active.value,
        onActiveChange = {
            active.value = it
            isVisibleMap.value = !it
        },
        modifier = modifier,
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingIcon = {
            if (active.value)
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        active.value = false
                        isVisibleMap.value = true
                    }
                )
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        tonalElevation = 0.dp,
    ) {
        Column {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                ChipFilter("Rashid")
                ChipFilter("Rashid")
                ChipFilter("Rashid")
            }
            HorizontalDivider(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                thickness = 1.dp
            )
            LazyColumn {
                items(filteredItems.size) {
                    Row (modifier = Modifier.padding(5.dp)){
                        Icon(
                            modifier = Modifier.padding(5.dp).size(20.dp),
                            imageVector = Icons.Rounded.Refresh, contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = filteredItems[it]
                        )
                    }
                }
            }
        }
    }
}