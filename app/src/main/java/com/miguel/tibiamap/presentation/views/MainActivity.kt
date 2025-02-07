package com.miguel.tibiamap.presentation.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.miguel.tibiamap.R
import com.miguel.tibiamap.domain.models.RashidData
import com.miguel.tibiamap.maps.ImageZip
import com.miguel.tibiamap.maps.TibiaMap
import com.miguel.tibiamap.presentation.components.ToolTipMaker
import com.miguel.tibiamap.presentation.ViewModels.ViewModelMap
import com.miguel.tibiamap.presentation.components.ChipFilter
import com.miguel.tibiamap.presentation.components.MainSearchBar
import com.miguel.tibiamap.presentation.components.TickerView
import com.miguel.tibiamap.presentation.components.npcinformationdefault.ModalNpcInformation
import com.miguel.tibiamap.presentation.viewmodelfactories.ViewModelMapFactory
import com.miguel.tibiamap.ui.theme.TibiaMapTheme
import com.miguel.tibiamap.utils.JsonInfo
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.centroidX
import ovh.plrapps.mapcompose.api.centroidY
import ovh.plrapps.mapcompose.api.enableFlingZoom
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.api.maxScale
import ovh.plrapps.mapcompose.api.minScale
import ovh.plrapps.mapcompose.api.removeAllMarkers
import ovh.plrapps.mapcompose.api.rotation
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.api.scroll
import ovh.plrapps.mapcompose.api.setStateChangeListener
import ovh.plrapps.mapcompose.api.shouldLoopScale
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import org.koin.android.ext.android.inject
import ovh.plrapps.mapcompose.api.scrollTo

@Suppress("NAME_SHADOWING", "UNREACHABLE_CODE")
class MainActivity : ComponentActivity() {
    private val imageZip = ImageZip(this)
    private val jsonInfo = JsonInfo()
    private val tibiaMaps = TibiaMap()
    private lateinit var tileStreamProvider:TileStreamProvider
    val urlRashidImage = "https://raw.githubusercontent.com/MiguelJeronimo/TtoolsDesktop/main/src/img/rashid.gif"
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    @SuppressLint("DiscouragedApi", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory: ViewModelMapFactory by inject()
        val viewModel = ViewModelProvider(this, factory)[ViewModelMap::class.java]
        viewModel.searchNPC("Rashid")
        val coordinates = R.raw.coordinates
        val markersJson = R.raw.markers
        val jsonRashid = R.raw.rashid_location
        val stringjson = jsonInfo.readJSON(context = this, resId = coordinates)
        val stringMarkerJson = jsonInfo.readJSON(context = this, resId = markersJson)
        val makersJson = jsonInfo.readMaker(stringMarkerJson!!)
        // val configuration = configurationCoordinates.getCoordinates(stringjson!!)
        println("Makers: $makersJson")
        //val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        //println("Max Memory: $maxMemory")
        enableEdgeToEdge()
        setContent {
            TibiaMapTheme {
                val floor = remember { mutableIntStateOf(7) }
                val scaleState = remember { mutableFloatStateOf(0f) }
                val scrollState = remember { mutableStateOf(Pair(0.0, 0.0)) }
                val x = remember { mutableDoubleStateOf(0.0) }
                val y = remember { mutableDoubleStateOf(0.0) }
                val rotation = remember { mutableFloatStateOf(0f) }
                //val tileCache = remember { LruCache<String, InputStream>(maxMemory) }
                val markerVisibility = remember { mutableStateOf(false) }
                //state of bottomsheets
                val sheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()
                val showBottomSheet = remember { mutableStateOf(false) }

                val rashidUbucationState = remember { mutableStateOf(RashidData()) }

                viewModel.scale.observe(this){
                    scaleState.floatValue = it.toFloat()
                }

                viewModel.scroll.observe(this){
                    println("Scroll ViewModel: $it")
                    scrollState.value = it
                }

                viewModel.rotation.observe(this){
                   rotation.floatValue = it
                }

                viewModel.searchNpc.observe(this){
                    println("NPC: $it")
                }

                viewModel.rashid.observe(this){
                   if (it != null){
                       rashidUbucationState.value = it
                   }
                }

                viewModel.floor.observe(this){
                    println("FLOOR VIEWMODEL $it")
                    println("AGREGANDO COORDENADAS: ${scrollState.value}")
                    floor.intValue = it
                }
                println("Valor de Floor en tileProvider---- ${floor.intValue}")
                tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
                    println("Valor de Floor en tileProvider ${floor.intValue}")
                    println("COORDENADAS: ($row, $col,  $zoomLvl) - FLOOR: ${floor.intValue}")
//                    println("Row: $row, Col: $col, ZoomLvl: $zoomLvl")
                    val image = "tibiamaps/${floor.intValue}/$zoomLvl/${col}/$row.png"
                    println("Image: $image")
                    imageZip.unzip2(image, this)
//                    val bitmap = tileCache.get("$zoomLvl/${col}/$row.png")
//                    if (bitmap != null) {
//                        println("Bitmap: $bitmap")
//                        return@TileStreamProvider ZipInputStream(bitmap)
//                    } else {
//                        val imageZip = imageZip.unzip2(image, this)
//                        tileCache.put(image, imageZip)
//                        return@TileStreamProvider imageZip
//                    }
                }
                //width: 10 imagenes x 8 height
                val state = MapState(
                    levelCount = 1,
                    fullWidth = 2560 ,
                    fullHeight = 2048,
                    tileSize = 256,
                    workerCount = 6
                ){
                    scale(scaleState.floatValue)
                    maxScale(15f)
                    rotation(rotation.floatValue)
                    scroll(x = scrollState.value.first, y = scrollState.value.second)
                    println("MAPsTATE: ${scrollState.value}, sCALE: ${scaleState.floatValue}")
                }.apply {
                    shouldLoopScale = true
                    //Agregar la capa del mapa con el TileStreamProvider
                    if (markerVisibility.value){
                        makersJson.forEach {
                            if (it.z == floor.intValue) {
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
                    }

                    if (!markerVisibility.value){
                        removeAllMarkers()
                    }
                    addLayer(tileStreamProvider)
                    enableFlingZoom()
                    enableRotation()
                }
                state.setStateChangeListener {
                    println("Scale: ${state.scale}")
                    println("Max Scale: ${state.maxScale}")
                    println("Min Scale: ${state.minScale}")
                    println("Rotate: ${state.rotation}")
                    println("centroIDX: ${state.centroidX}")
                    println("CentroIdY: ${state.centroidY}")
                    println("Scroll: ${state.scroll.x}")
                    println("Scroll: ${state.scroll.y}")
                    //viewModel.setScrollTo(state.scroll.x, state.scroll.y)
                    x.doubleValue = state.centroidX
                    y.doubleValue = state.centroidY
                    println("//////////////////////////////////")
                }
                //Ubucation of rashid
                if (rashidUbucationState.value.city != null){
                    state.removeAllMarkers()
                    println("UBICACION STATE: ${rashidUbucationState.value}")
                    LaunchedEffect(Unit) {
                        viewModel.setFloor(rashidUbucationState.value.floor!!)
                        state.scrollTo(
                            x = tibiaMaps.pixelInX(rashidUbucationState.value.x!!),
                            y = tibiaMaps.pixelInY(rashidUbucationState.value.y!!),
                        )
                        state.addMarker(
                            id = "Rashid NPC",
                            x = tibiaMaps.pixelInX(rashidUbucationState.value.x!!),
                            y = tibiaMaps.pixelInY(rashidUbucationState.value.y!!)
                        ){
                            GlideImage(
                                model = urlRashidImage,
                                contentDescription = "Rashid",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        showBottomSheet.value = !showBottomSheet.value
                                    }
                            )
                            showBottomSheet.value = true
                        }
                    }

                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (showBottomSheet.value) {
                        ModalNpcInformation(
                            modifier = Modifier.fillMaxHeight(),
                            onDismissRequest = {
                                showBottomSheet.value = false
                            },
                            sheetState = sheetState,
                            showBottomSheet = showBottomSheet,
                            scope = scope
                        )
                    }
                    Box(Modifier.fillMaxSize()) {
                        val isVisibleMap = remember { mutableStateOf(true) }
                        if (isVisibleMap.value) {
                            MapContainer(
                                modifier = Modifier
                                    .fillMaxSize(),
                                state = state
                            )
                        }
                        MainSearchBar(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.TopCenter),
                                    //.zIndex(1f),
                                isVisibleMap = isVisibleMap,
                            jsonRashid = jsonRashid,
                            context = this@MainActivity
                        )
                        Column (
                            Modifier
                                .align(Alignment.CenterEnd)
                                .padding(10.dp)
                        ){
                            FloatingActionButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if (floor.intValue > 0) {
                                        //viewModel.setOnClickUp(true)
                                        val floorState = floor.intValue
                                        viewModel.setFloor(floorState - 1)
                                        viewModel.setScale(state.scale)
                                        viewModel.setRotation(state.rotation)
                                        println("AGREGANDO COORDENADAS: ${state.centroidX}, ${state.centroidY}")
                                        viewModel.setScrollTo(state.centroidX, state.centroidY)
                                    }
                                }
                            ) {Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "") }
                            TickerView(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(5.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = tibiaMaps.realFloor(floor.intValue).toString(),
                                color = Color.White,
                                size = 45f,
                            )
                            FloatingActionButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if (floor.intValue < 15) {
                                        val floorState = floor.intValue
                                        viewModel.setFloor(floorState + 1)
                                        viewModel.setScale(state.scale)
                                        viewModel.setRotation(state.rotation)
                                        viewModel.setScrollTo(state.centroidX, state.centroidY)
                                    }
                                },
                            ) {
                                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "")
                            }
                        }

                        FloatingActionButton(
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.CenterStart),
                            onClick = {
                                //showBottomSheet.value = true
                                markerVisibility.value = !markerVisibility.value
                            }
                        ) {Icon(Icons.Filled.LocationOn, contentDescription = "") }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            Text(
                                text = "X: ${x.doubleValue.toFloat()} Y: ${y.doubleValue.toFloat()}; Z: ${tibiaMaps.realFloor(floor.intValue)}",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(0.dp),
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Tibia Map by Mike",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
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