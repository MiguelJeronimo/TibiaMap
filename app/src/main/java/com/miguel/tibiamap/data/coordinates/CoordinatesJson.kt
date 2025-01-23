package com.miguel.tibiamap.data.coordinates

import com.google.gson.Gson
import com.miguel.tibiamap.domain.Coordinate
import com.miguel.tibiamap.domain.Coordinates

class CoordinatesJson {
    fun getCoordinates(data: String): Coordinates? {
        val gson = Gson()
        val coordinates = gson.fromJson(data, Coordinates::class.java)
        return coordinates
    }

    fun searchCoordinateData(col: Int, row: Int, floor: Int, coordinates: List<Coordinate>): Coordinate? {
        val result = coordinates.find { it.col == col && it.row == row && it.floor == floor }
        return result
    }

}