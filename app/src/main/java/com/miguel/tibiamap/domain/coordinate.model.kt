package com.miguel.tibiamap.domain


data class Coordinates(
    val coordinates: Floor
)

data class Floor(
    val level0: List<Coordinate>,
    val level3: List<Coordinate>,
    val level4: List<Coordinate>,
    val level5: List<Coordinate>,
    val level6: List<Coordinate>,
    val level7: List<Coordinate>,
    val level8: List<Coordinate>,
    val level9: List<Coordinate>,
    val level10: List<Coordinate>,
    val level11: List<Coordinate>,
    val level12: List<Coordinate>,
    val level13: List<Coordinate>,
    val level14: List<Coordinate>,
    val level15: List<Coordinate>,
)

data class Coordinate(
    val col: Int,
    val row: Int,
    val image_name_number: String,
    val floor: Int
)