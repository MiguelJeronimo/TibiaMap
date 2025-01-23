package com.miguel.tibiamap

import com.miguel.tibiamap.data.network.ApiClientServices
import com.miguel.tibiamap.data.network.RetrofitClient
import com.miguel.tibiamap.data.repositories.NpcMetaDataRepository
import com.miguel.tibiamap.data.repositories.NpcMetaDataRepositoryImp
import com.miguel.tibiamap.domain.usecase.UseCaseNpcMetadata
import com.miguel.tibiamap.presentation.ViewModels.ViewModelMap
import com.miguel.tibiamap.presentation.viewmodelfactories.ViewModelMapFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class Di {
    val appModule = module {
        //NpcMetaData info
        val url = "https://raw.githubusercontent.com/s2ward/tibia/8824eb38872a1174b0f0c923e08719e981f32ecc/data/"
        val retrofit = RetrofitClient().getRetrofit(url).create(ApiClientServices::class.java)
        single<NpcMetaDataRepository> {
            NpcMetaDataRepositoryImp(retrofit)
        }
        factory<UseCaseNpcMetadata> {
            UseCaseNpcMetadata(get())
        }
        single<ViewModelMapFactory> {
            ViewModelMapFactory(get())
        }
        viewModel<ViewModelMap> {
            ViewModelMap(get())
        }
    }
}