package com.miguel.tibiamap.domain.usecase

import com.miguel.tibiamap.data.repositories.NpcMetaDataRepository
import com.miguel.tibiamap.domain.models.Npc

class UseCaseNpcMetadata(private val repository: NpcMetaDataRepository) {
    suspend fun getNpcMetadata(): Npc? {
        return repository.getNpcMetaData()
    }
}