package com.miguel.tibiamap.data.network

import com.miguel.tibiamap.domain.models.Npc
import retrofit2.Response
import retrofit2.http.GET

interface ApiClientServices {
    @GET("npcs/npc_metadata.json")
    suspend fun getNpcMetadata(): Response<Npc>
}