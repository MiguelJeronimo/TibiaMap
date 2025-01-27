package com.miguel.tibiamap.domain.usecase

import com.miguel.tibiamap.data.repositories.NpcMetaDataRepository
import com.miguel.tibiamap.domain.models.NpcItem
import com.miguel.tibiamap.utils.Searching


class UseCaseNpcMetadata(private val repository: NpcMetaDataRepository) {
    private val search = Searching()
    suspend fun getNpcMetadata(): ArrayList<NpcItem>? {
        return repository.getNpcMetaData()
    }

    suspend fun searchNPC(name: String): NpcItem? {
        val response = repository.getNpcMetaData()
        return if (response != null){
            search.searchNPC(name, response)
        } else {
            null
        }
    }

    suspend fun rashid(name: String): String? {
        val response = repository.rashid()
        return response
    }

}