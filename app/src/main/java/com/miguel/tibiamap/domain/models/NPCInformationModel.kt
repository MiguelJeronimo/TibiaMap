package com.miguel.tibiamap.domain.models

import com.google.gson.annotations.SerializedName

data class Npc(
    val npcList: ArrayList<NpcItem>
)
data class NpcItem(
    val name: String,
    val jobs: String,
    val race: String,
    val gender: String,
    val location: String,
    val subarea: String,
    val map: String,
    val version: String,
    val quest: Quest,
    val dialogues: ArrayList<String>,
    val coordiantes: ArrayList<Int>
)

data class Quest(
    @SerializedName("quest-name")
    val questName: String,
    @SerializedName("quest-url")
    val questUrl: String

)