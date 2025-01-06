package com.miguel.tibiamap.maps

import android.content.Context
import com.miguel.tibiamap.R
import com.miguel.tibiamap.domain.data.CoordinatesJson
import com.miguel.tibiamap.utils.JsonInfo
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ImageZip(private val context: Context) {
    private val jsonInfo = JsonInfo()
    // Datos de configuración proporcionados
    val xMin = 31744
    val xMax = 34048
    val yMin = 30976
    val yMax = 32768
    val width = 2560
    val height = 2048
    val floorIDs = (0..15).map { String.format("%02d", it) }

    // Calcular los rangos de las coordenadas
    val deltaX = xMax - xMin
    val deltaY = yMax - yMin

    fun unzip(
        row: Int,
        col: Int,
        zoomLvl: Int,
        configurationCoordinates: CoordinatesJson,
        coordinates: Int
    ): InputStream? {
        val stringjson = jsonInfo.readJSON(context = context, resId = coordinates)
        val configuration = configurationCoordinates.getCoordinates(stringjson!!)
        val coordinate = configurationCoordinates.searchCoordinateData(
            col = col,
            row = row,
            floor = 7,
            coordinates = configuration!!.coordinates.level7
        )
        val coordenadas = getTotalTiles(zoomLvl)
        println("coordenadas: $coordenadas")
        // Calculamos el número de tiles en función del zoom
//        val deltaX = xMax - xMin
//        val deltaY = yMax - yMin
//        val numTilesX = Math.pow(2.0, zoomLvl.toDouble()).toInt()
//        val numTilesY = Math.pow(2.0, zoomLvl.toDouble()).toInt()
//
//        // Calculamos el tamaño de cada tile en función del nivel de zoom
//        val tileWidth = deltaX / numTilesX
//        val tileHeight = deltaY / numTilesY
//
//        // Calculamos las coordenadas absolutas del tile
//        val tileXCoord = xMin + (col * tileWidth)
//        val tileYCoord = yMin + (row * tileHeight)
        val numTilesX = Math.pow(2.0, zoomLvl.toDouble()).toInt()
        val numTilesY = Math.pow(2.0, zoomLvl.toDouble()).toInt()

        val tileWidth = deltaX / numTilesX
        val tileHeight = deltaY / numTilesY

        // Coordenadas absolutas del tile
        val tileXCoord = xMin + (col * tileWidth)
        val tileYCoord = yMin + (row * tileHeight)
        //println("Tile requested: row=$row, col=$col, zoomLvl=$zoomLvl")
        //val tileName = "Minimap_Color_${tileXCoord}_${tileYCoord}_${7}.png"
        val tileName = "Minimap_Color_${coordinate?.image_name_number}_${coordinate?.floor}.png"
        // Ruta del archivo ZIP dentro de res/raw/
        val zipFileResourceId = R.raw.minimap_without_markers  // Reemplaza con el nombre real del archivo en raw
        return try {
            // Obtener InputStream del archivo ZIP en res/raw
            val zipInputStream: InputStream = context.resources.openRawResource(zipFileResourceId)
            // Crear ZipInputStream para leer el archivo ZIP
            val zipStream = ZipInputStream(zipInputStream)
            // Iterar sobre las entradas del archivo ZIP
            var entry: ZipEntry?
            while (zipStream.nextEntry.also { entry = it } != null) {
                // Si encontramos el tile dentro del ZIP, devolver el InputStream
                if (entry?.name == "minimap/$tileName") {  // Asegúrate de ajustar la ruta según cómo esté organizado el ZIP
                    //println("Entry: ${entry?.name}")
                    //println("TileName: minimap/$tileName")
                    return zipStream  // Devolver el InputStream del tile encontrado
                }
                zipStream.closeEntry()  // Cerrar la entrada si no es el tile buscado
            }
            // Si no se encuentra el tile, devolver null
            null
        } catch (e: Exception) {
            // Manejar cualquier error al acceder al archivo ZIP
            e.printStackTrace()
            null
        }
    }

    fun unzip2(title: String, context: Context): ZipInputStream? {
        return try {
            // Ruta del archivo ZIP dentro de res/raw/
            val zipFileResourceId = context.resources.openRawResource(R.raw.tibiamapscompose2)  // Reemplaza con el nombre real del archivo en raw
            // Crear ZipInputStream para leer el archivo ZIP
            val zipStream = ZipInputStream(zipFileResourceId)
            // Iterar sobre las entradas del archivo ZIP
            var entry: ZipEntry?
            while (zipStream.nextEntry.also { entry = it } != null) {
                // Si encontramos el tile dentro del ZIP, devolver el InputStream
                if (entry?.name == title) {  // Asegúrate de ajustar la ruta según cómo esté organizado el ZIP
                    println("Entry: ${entry?.name}")
                    //println("TileName: minimap/$title")
                    return zipStream  // Devolver el InputStream del tile encontrado
                }
                zipStream.closeEntry()  // Cerrar la entrada si no es el tile buscado
            }
            // Si no se encuentra el tile, devolver null
            null
        }catch (e: Exception){
            null
        }
    }


    private fun calculateAbsoluteCoordinates(row: Int, col: Int, zoomLvl: Int): Pair<Int, Int> {
        val tileSize = 256 // Tamaño del tile en píxeles
        val scaleFactor = 1 shl zoomLvl // 2^zoomLvl
        val absoluteX = col * tileSize * scaleFactor
        val absoluteY = row * tileSize * scaleFactor
        return Pair(absoluteX, absoluteY)
    }

    fun getTotalTiles(zoomLvl: Int): Pair<Int, Int> {
        val totalTiles = 1 shl zoomLvl // 2^zoomLvl
        return Pair(totalTiles, totalTiles)
    }

}