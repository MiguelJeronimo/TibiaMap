package com.miguel.tibiamap.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipFilter(text: String, state: MutableState<Boolean>? = null){
    var selected = rememberSaveable { mutableStateOf(true) }
    state?.value = selected.value
    FilterChip(
        modifier = Modifier.padding(5.dp, 0.dp, 5.dp, 0.dp),
        onClick = {
            selected.value = !selected.value
            state?.value = selected.value
        },
        label = {
            Text(text)
        },
        selected = selected.value,
        leadingIcon = if (selected.value) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}