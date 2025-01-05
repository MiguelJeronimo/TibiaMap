package com.miguel.tibiamap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState

class MainActivity : ComponentActivity() {
    private val imageZip = ImageZip(this)
    private val cooordiantes = CoordinatesJson()
    private val jsonInfo = JsonInfo()
    private val tibiaMaps = TibiaMap()
    private val configurationCoordinates = CoordinatesJson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coordinates = R.raw.coordinates
        val stringjson = jsonInfo.readJSON(context = this, resId = coordinates)
        val configuration = configurationCoordinates.getCoordinates(stringjson!!)
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
                    val image = "Minimap_Color_${coordinate?.image_name_number}_${coordinate?.floor}.png"
                    println("Image: $image")
                    //imageZip.unzip(row, col, zoomLvl)
                    imageZip.unzip2(image, this)
                }
                //width: 10 imagenes x 8 height
                val state = MapState(
                    1,
                    2560 ,
                    2048
                ).apply {
                    println("MAAAAAAAAAAAP: ${titleStreamProvider}")
                    // Agregar la capa del mapa con el TileStreamProvider
                    addMarker(
                        id = "Temple Thais",
                        clickable = true,
                        x = tibiaMaps.pixelInX(32369)!!,
                        y = tibiaMaps.pixelInY(32241)!!
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = Color.DarkGray
                        )
                    }
                    addMarker(
                        id = "DP THAIS",
                        clickable = true,
                        x = tibiaMaps.pixelInX(32347)!!,
                        y = tibiaMaps.pixelInY(32226)!!
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = Color.DarkGray
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