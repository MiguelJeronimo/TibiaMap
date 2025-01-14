package com.miguel.tibiamap.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miguel.tibiamap.maps.TibiaMap
import ovh.plrapps.mapcompose.ui.state.MapState

class ViewModelMap: ViewModel() {
    private val tibiaMaps = TibiaMap()
    private val _scale = MutableLiveData<Float>()
    private val _mapState = MutableLiveData<MapState>()
    private val _rotation = MutableLiveData<Float>()
    private val _floor = MutableLiveData<Int>()
    private val _scroll = MutableLiveData<Pair<Double, Double>>()

    val scale: MutableLiveData<Float> get() = _scale
    val mapState: MutableLiveData<MapState> get() = _mapState
    val rotation: MutableLiveData<Float> get() = _rotation
    val floor: MutableLiveData<Int> get() = _floor
    val scroll: MutableLiveData<Pair<Double, Double>> get() = _scroll

    init {
        scale.value = 15.0F
        scroll.value = Pair(tibiaMaps.pixelInX(32369), tibiaMaps.pixelInY(32241))
        rotation.value = 0.0F
    }


    fun setScale(value: Float) {
        _scale.value = value
    }

    fun setMapState(value: MapState) {
        _mapState.value = value
    }

    fun setRotation(value: Float) {
        _rotation.value = value
    }

    fun setFloor(value: Int) {
        _floor.value = value
    }

    fun setScrollTo(x: Double, y: Double) {
        _scroll.value = Pair(x, y)
    }
}