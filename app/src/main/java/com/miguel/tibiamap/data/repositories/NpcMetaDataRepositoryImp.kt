package com.miguel.tibiamap.data.repositories

import com.miguel.tibiamap.data.network.ApiClientServices
import com.miguel.tibiamap.domain.models.NpcItem

class NpcMetaDataRepositoryImp(private val apiClientServices: ApiClientServices):
    NpcMetaDataRepository {
    override suspend fun getNpcMetaData(): ArrayList<NpcItem>? {
        val response = apiClientServices.getNpcMetadata()
        return try {
            if (response.isSuccessful){
                response.body()!!
            } else {
                null
            }
        } catch (e: Exception){
            return null
        }
    }

    override suspend fun rashid(): String? {
        val response = apiClientServices.rashidLocation()
        return try {
            if (response.isSuccessful){
                response.body()!!
            } else {
                null
            }
        } catch (e: Exception){
            return null
        }
    }
}