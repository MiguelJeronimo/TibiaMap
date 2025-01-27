package com.miguel.tibiamap.utils

import com.miguel.tibiamap.domain.models.NpcItem

class Searching {
    fun searchNPC(name:String, npcList: ArrayList<NpcItem>): NpcItem? {
        return npcList.find { it.name == name.replace("","") }
    }
}