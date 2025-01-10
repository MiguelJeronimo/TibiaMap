package com.miguel.tibiamap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miguel.tibiamap.domain.data.CoordinatesJson
import com.miguel.tibiamap.maps.ImageZip
import com.miguel.tibiamap.maps.TibiaMap
import com.miguel.tibiamap.presentation.ToolTipMaker
import com.miguel.tibiamap.presentation.components.MainSearchBar
import com.miguel.tibiamap.ui.theme.TibiaMapTheme
import com.miguel.tibiamap.utils.JsonInfo
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.enableFlingZoom
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.api.maxScale
import ovh.plrapps.mapcompose.api.minScale
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.api.shouldLoopScale
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState

class MainActivity : ComponentActivity() {
    private val imageZip = ImageZip(this)
    private val cooordiantes = CoordinatesJson()
    private val jsonInfo = JsonInfo()
    private val tibiaMaps = TibiaMap()
    private val configurationCoordinates = CoordinatesJson()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("DiscouragedApi", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coordinates = R.raw.coordinates
        val markersJson = R.raw.markers
        val stringjson = jsonInfo.readJSON(context = this, resId = coordinates)
        val stringMarkerJson = jsonInfo.readJSON(context = this, resId = markersJson)
        val makersJson = jsonInfo.readMaker(stringMarkerJson!!)
        // val configuration = configurationCoordinates.getCoordinates(stringjson!!)
        println("Makers: $makersJson")
        enableEdgeToEdge()
        setContent {
            TibiaMapTheme {
                val floor = remember { mutableIntStateOf(7) }
                val titleStreamProvider = TileStreamProvider { row, col, zoomLvl ->
                    println("Row: $row, Col: $col, ZoomLvl: $zoomLvl")
//                    val coordinate = configurationCoordinates.searchCoordinateData(
//                        col = col,
//                        row = row,
//                        floor = 7,
//                        coordinates = configuration!!.coordinates.level7
//                    )
                    //val image = "Minimap_Color_${coordinate?.image_name_number}_${7}.png"
                    //tibiamapscomponse2
                    //val image2 = "maps/$zoomLvl/${col}/$row.png"
                    val image2 = "tibiamaps/${floor.intValue}/$zoomLvl/${col}/$row.png"
                    //val google = "google2/$zoomLvl/${col}_${row}.png"
                    println("Image: $image2")
                    //imageZip.unzip(row, col, zoomLvl)
                    imageZip.unzip2(image2, this)
                }
                //width: 10 imagenes x 8 height
                val state = MapState(
                    levelCount = 1,
                    fullWidth = 2560 ,
                    fullHeight = 2048,
                    tileSize = 256,
                    workerCount = 4
                ){
                    scale(0.81f)
                    maxScale(15f)
                }.apply {
                    shouldLoopScale = true
                    //Agregar la capa del mapa con el TileStreamProvider
                    makersJson.forEach {
                        if (it.z == floor.intValue) {
                            println("Z: ${it.z}")
                            addMarker(
                                id = it.description,
                                x = tibiaMaps.pixelInX(it.x),
                                y = tibiaMaps.pixelInY(it.y)
                            ) {
                                //get id to resource image with name string
                                val tooltipState = rememberTooltipState(isPersistent = true)
                                val scope = rememberCoroutineScope()
                                val resId = resources.getIdentifier(
                                    tibiaMaps.imageName(it.icon),
                                    "drawable",
                                    packageName
                                )
                                ToolTipMaker(
                                    scope = scope,
                                    description = it.description,
                                    icon = resId,
                                    tooltipState = tooltipState
                                )
                            }
                        }
                    }
                    addLayer(titleStreamProvider)
                    enableFlingZoom()
                    enableRotation()
                }

                println("Floor: ${floor.intValue}")
                //Mandamos la marca al dp thais
                LaunchedEffect(Unit) {
                    state.scrollTo(
                        x = tibiaMaps.pixelInX(32369),
                        y = tibiaMaps.pixelInY(32241),
                        destScale = 4f
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(Modifier.fillMaxSize()) {
                        val isVisibleMap = remember { mutableStateOf(true) }
                        MainSearchBar(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.TopCenter),
                                isVisibleMap = isVisibleMap
                        )
                        if (isVisibleMap.value) {
                            MapContainer(
                                modifier = Modifier
                                    .fillMaxSize(),
                                state = state
                            )
                        }

                        Column (
                            Modifier
                                .align(Alignment.CenterEnd)
                                .padding(10.dp)
                        ){
                            FloatingActionButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if (floor.intValue > 0) {
                                        floor.intValue--
                                    }
                                }
                            ) {Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "") }
                            Text(
                                text = floor.intValue.toString(),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.CenterHorizontally),
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium
                            )
                            FloatingActionButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if (floor.intValue < 15) {
                                        floor.intValue++
                                    }
                                },
                            ) {
                                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "")
                            }
                        }

                        Text(
                            text = "Tibia Map by Mike",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(25.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

/* Inside a composable */
@Composable
fun MapContainer(
    modifier: Modifier = Modifier, state: MapState,
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