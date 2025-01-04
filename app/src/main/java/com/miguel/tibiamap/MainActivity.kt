package com.miguel.tibiamap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.miguel.tibiamap.maps.ImageZip
import com.miguel.tibiamap.ui.theme.TibiaMapTheme
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.File
import java.io.FileInputStream

class MainActivity : ComponentActivity() {
    private val imageZip = ImageZip(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TibiaMapTheme {
                val titleStreamProvider = TileStreamProvider{ row, col, zoomLvl ->
                    imageZip.unzip(row, col, zoomLvl)
                }

                //width: 10 imagenes x 8 height
                val state = MapState(15, 2560, 2048).apply {
                    println("MAAAAAAAAAAAP: ${titleStreamProvider}")
                    // Agregar la capa del mapa con el TileStreamProvider
                    addLayer(titleStreamProvider)
                    enableRotation()

                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MapContainer(
                            modifier = Modifier.padding(innerPadding).fillMaxSize(),
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