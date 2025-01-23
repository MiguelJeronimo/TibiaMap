package com.miguel.tibiamap.data.repositories

import com.miguel.tibiamap.domain.models.Npc

interface NpcMetaDataRepository {
    suspend fun getNpcMetaData(): Npc?
}