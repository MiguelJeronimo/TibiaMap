package com.miguel.tibiamap.data.repositories

import com.miguel.tibiamap.domain.models.NpcItem

interface NpcMetaDataRepository {
    suspend fun getNpcMetaData(): ArrayList<NpcItem>?
    suspend fun rashid():String?
}