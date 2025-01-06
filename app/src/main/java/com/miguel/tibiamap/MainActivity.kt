package com.miguel.tibiamap

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miguel.tibiamap.domain.data.CoordinatesJson
import com.miguel.tibiamap.maps.ImageZip
import com.miguel.tibiamap.maps.TibiaMap
import com.miguel.tibiamap.ui.theme.TibiaMapTheme
import com.miguel.tibiamap.utils.JsonInfo
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.addPath
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.api.rotateTo
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState

class MainActivity : ComponentActivity() {
    private val imageZip = ImageZip(this)
    private val cooordiantes = CoordinatesJson()
    private val jsonInfo = JsonInfo()
    private val tibiaMaps = TibiaMap()
    private val configurationCoordinates = CoordinatesJson()
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coordinates = R.raw.coordinates
        val markersJson = R.raw.markers
        val stringjson = jsonInfo.readJSON(context = this, resId = coordinates)
        val stringMarkerJson = jsonInfo.readJSON(context = this, resId = markersJson)
        val makersJson = jsonInfo.readMaker(stringMarkerJson!!)
        val configuration = configurationCoordinates.getCoordinates(stringjson!!)
        println("Makers: $makersJson")
        enableEdgeToEdge()
        setContent {
            TibiaMapTheme {
                val titleStreamProvider = TileStreamProvider{ row, col, zoomLvl ->
                    println("Row: $row, Col: $col, ZoomLvl: $zoomLvl")
                    val coordinate = configurationCoordinates.searchCoordinateData(
                        col = col,
                        row = row,
                        floor = 7,
                        coordinates = configuration!!.coordinates.level7
                    )
                    val image = "Minimap_Color_${coordinate?.image_name_number}_${7}.png"
                    //tibiamapscomponse2
                    val image2 = "tibiamapscompose2/$zoomLvl/${col}/$row.png"
                    println("Image: $image2")
                    //imageZip.unzip(row, col, zoomLvl)
                    imageZip.unzip2(image2, this)
                }
                //width: 10 imagenes x 8 height
                val state = MapState(
                    1,
                    2560 ,
                    2048
                ).apply {
                    // Agregar la capa del mapa con el TileStreamProvider
//                    makersJson.forEach {
//                        if (it.z == 7){
//                            println("Z: ${it.z}")
//                            addMarker(
//                                id = it.description,
//                                clickable = true,
//                                x = tibiaMaps.pixelInX(it.x),
//                                y = tibiaMaps.pixelInY(it.y)
//                            ){
//                                //get id to resource image with name string
//                                val resId = resources.getIdentifier(tibiaMaps.imageName(it.icon), "drawable", packageName)
//                                val imageBitmap = BitmapFactory.decodeResource(resources, resId)
//                                println("ID: $resId")
//                                println("ID: ${R.drawable.up}")
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                                    contentDescription = null,
//                                    modifier = Modifier.size(50.dp),
//                                    tint = Color.Black
//                                )
//                            }
//                        }
//                    }
                    addMarker(
                        id = "it.description",
                        clickable = true,
                        x = tibiaMaps.pixelInX(32369),
                        y = tibiaMaps.pixelInY(32241)
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.size(10.dp),
                            tint = Color.Black
                        )
                    }
                    addLayer(titleStreamProvider)
                    enableRotation()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MapContainer(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            state = state
                        )
                }
            }
        }
    }
}

/* Inside a composable */
@Composable
fun MapContainer(
    modifier: Modifier = Modifier,  state: MapState
) {
    MapUI(modifier, state = state)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TibiaMapTheme {
        Greeting("Android")
    }
}