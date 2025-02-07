package com.miguel.tibiamap.presentation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.miguel.tibiamap.presentation.ViewModels.ViewModelMap
import com.miguel.tibiamap.utils.lists.listNPC
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSearchBar(
    modifier: Modifier = Modifier,
    isVisibleMap: MutableState<Boolean>,
    viewModelMap: ViewModelMap = koinViewModel<ViewModelMap>(),
    jsonRashid: Int? = null,
    context: Context? = null
) {
    val searchQuery = remember { mutableStateOf("") }
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

    val active = remember { mutableStateOf(false) }
    val filteredItems =
        items.filter { it.contains(searchQuery.value, ignoreCase = true) }

    Column {
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
                    ChipAsisst(
                        "Rashid",
                        onClick = {
                            viewModelMap.searchRashid(
                                name = "Rashid",
                                context = context!!,
                                jsonId = jsonRashid!!
                            )
                        }
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    thickness = 1.dp
                )
                LazyColumn {
                    items(filteredItems.size) {
                        Row(modifier = Modifier.padding(5.dp)) {
                            Icon(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(20.dp),
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

        if (!active.value) {
            val npcDefault = listNPC()
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.Center) {
                items(npcDefault.size){
                    ChipAsisst(
                        modifier = Modifier.padding(5.dp),
                        text = npcDefault[it].name,
                        onClick = {
                           if (npcDefault[it].name == "Rashid"){
                               viewModelMap.searchRashid(
                                   name = "Rashid",
                                   context = context!!,
                                   jsonId = jsonRashid!!
                               )
                           }
                        }
                    )
                }
            }
        }
    }

}