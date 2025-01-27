package com.miguel.tibiamap.data.network

import com.miguel.tibiamap.domain.models.NpcItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiClientServices {
    @GET("npcs/npc_metadata.json")
    suspend fun getNpcMetadata(): Response<ArrayList<NpcItem>>

    @GET("rashid")
    suspend fun rashidLocation(): Response<String>
}