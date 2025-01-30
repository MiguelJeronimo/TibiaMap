package com.miguel.tibiamap.utils

import com.google.gson.Gson
import com.miguel.tibiamap.domain.models.NpcItem
import com.miguel.tibiamap.domain.models.RashidData
import com.miguel.tibiamap.domain.models.RashidLocations

class Searching: JsonInfo() {
    fun searchNPC(name:String, npcList: ArrayList<NpcItem>): NpcItem? {
        return npcList.find { it.name == name.replace("","") }
    }
    fun rashidUbication(data: ArrayList<RashidData> , city: String): RashidData? {
        return data.find { it.city == city}
    }

    fun rashidGson(jsonString:String): RashidLocations? {
        val gson = Gson()
        //json is a list
        val rashid = gson.fromJson(jsonString, RashidLocations::class.java)
        return rashid
    }
}