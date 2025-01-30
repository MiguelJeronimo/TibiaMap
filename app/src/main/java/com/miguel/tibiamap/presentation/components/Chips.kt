package com.miguel.tibiamap.presentation.components

import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.miguel.tibiamap.R
import com.miguel.tibiamap.presentation.ViewModels.ViewModelMap
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChipFilter(text: String, state: MutableState<Boolean>? = null){
    val selected = rememberSaveable { mutableStateOf(true) }
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
@Composable
fun ChipAsisst(
    text: String,
    state: MutableState<Boolean>? = null,
    viewModelMap: ViewModelMap = koinViewModel<ViewModelMap>(),
    onClick: () -> Unit
){
    AssistChip(
        onClick = onClick,
        label = {
            Text(text)
        },
        leadingIcon = {
            Icon(
//                imageVector = R.drawable.bag,
                painter = painterResource(R.drawable.baseline_hail_24),
                contentDescription = "Done icon",
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        }
    )
}