package com.miguel.tibiamap.maps

@Suppress("UNUSED_EXPRESSION")
class TibiaMap {
    private val xMin = 31744
    private val xMax =  34048
    private val yMin = 30976
    private val yMax = 32768
    private val zMin = 0
    private val zMax = 15
    private val width = 2560.0
    private val height = 2048.0

    fun pixelInX(x: Int): Double {
            val pixelX = (x - 31744).toDouble() / (34303 - 31744).toDouble()
            println("PixelX: ${pixelX}")
            val offsetX = 0.000009
            return pixelX + offsetX
    }

    fun pixelInY(y: Int): Double {
            val pixelY = (y - 30976).toDouble() / (33023 - 30976).toDouble()
            println("PixelY: ${pixelY}")
            val offsetY = 0.0010
            return pixelY + offsetY
    }

    fun imageName(name:String): String {
        val image = when(name){
            "red down"->"red_down"
            "red up"->"red_up"
            "red left"->"red_left"
            "red right"->"red_right"
            "?"-> "question"
            "!"->"exclamation"
            "$"-> "dollar"
            else->name
        }
        println("Image2: $image")
        return image
    }

    fun realFloor(floor: Int): Int{
        return (floor-7)*(-1)
    }

}