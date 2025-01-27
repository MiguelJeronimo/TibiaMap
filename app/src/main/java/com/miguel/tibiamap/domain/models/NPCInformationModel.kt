package com.miguel.tibiamap.domain.models

import com.google.gson.annotations.SerializedName

data class NpcItem(
    val name: String,
    val job: String,
    val race: String,
    val gender: String,
    val location: String,
    val subarea: String,
    val map: String,
    val version: String,
    val quests: ArrayList<Quest>,
    val dialogues: ArrayList<String>,
    val coordinates: ArrayList<Int>
)

data class Quest(
    @SerializedName("quest-name")
    val questName: String,
    @SerializedName("quest-url")
    val questUrl: String

)