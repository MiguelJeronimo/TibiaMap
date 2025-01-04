package com.miguel.tibiamap.ViewModels

import androidx.lifecycle.ViewModel
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.api.onMarkerClick
import ovh.plrapps.mapcompose.api.shouldLoopScale
import ovh.plrapps.mapcompose.ui.state.MapState

class ViewModelMap: ViewModel() {
//    val state: MapState = MapState(4, 4096, 4096) {
//        scale(0.81f)
//        maxScale(8f)
//    }.apply {
//        addLayer()
//        enableRotation()
//        shouldLoopScale = true
//        onMarkerClick { id, x, y ->
//            println("on marker click $id $x $y")
//        }
//    }

}