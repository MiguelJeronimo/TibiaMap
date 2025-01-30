package com.miguel.tibiamap.domain.usecase

import android.content.Context
import com.miguel.tibiamap.data.repositories.NpcMetaDataRepository
import com.miguel.tibiamap.domain.models.NpcItem
import com.miguel.tibiamap.domain.models.RashidData
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

    suspend fun rashid(name: String, context:Context, jsonId: Int): RashidData? {
        val response = repository.rashid()
        if (response != null){
            println("CITY: $response")
            val jsonString = search.readJSON(context, jsonId)
            val rashidInfo = search.rashidGson(jsonString!!)
            val ubication = search.rashidUbication(
                data = rashidInfo!!.rashid,
                city = response
            )
            println("UBICACION: $ubication")
            return ubication
        } else {
            return null
        }
    }

}