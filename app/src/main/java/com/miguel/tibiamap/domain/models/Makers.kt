package com.miguel.tibiamap.domain.models


data class Marker (
    val x: Int,
    val y: Int,
    val z: Int,
    val icon: String,
    val description: String
)