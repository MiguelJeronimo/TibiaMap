package com.miguel.tibiamap.domain.models

data class RashidLocations(
    val rashid: ArrayList<RashidData>
)

data class RashidData(
    val day: String? = null,
    val city: String? = null,
    val x: Int? = null,
    val y: Int? = null,
    val floor: Int? = null
)

