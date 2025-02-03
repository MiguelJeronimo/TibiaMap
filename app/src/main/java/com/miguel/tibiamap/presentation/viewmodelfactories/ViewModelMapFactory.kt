package com.miguel.tibiamap.presentation.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miguel.tibiamap.domain.usecase.UseCaseNpcMetadata
import com.miguel.tibiamap.presentation.ViewModels.ViewModelMap

@Suppress("UNCHECKED_CAST")
class ViewModelMapFactory(private val useCaseNpcMetadata: UseCaseNpcMetadata): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelMap::class.java)) {
            return ViewModelMap(useCaseNpcMetadata = useCaseNpcMetadata) as T
        }
        throw IllegalArgumentException("Unknown ViewModelMap class")
    }
}