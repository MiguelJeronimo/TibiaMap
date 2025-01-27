package com.miguel.tibiamap.presentation.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.tibiamap.domain.models.NpcItem
import com.miguel.tibiamap.domain.usecase.UseCaseNpcMetadata
import com.miguel.tibiamap.maps.TibiaMap
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.ui.state.MapState

class ViewModelMap(private val useCaseNpcMetadata: UseCaseNpcMetadata): ViewModel() {
    private val tibiaMaps = TibiaMap()
    private val _scale = MutableLiveData<Float>()
    private val _mapState = MutableLiveData<MapState>()
    private val _rotation = MutableLiveData<Float>()
    private val _floor = MutableLiveData<Int>()
    private val _scroll = MutableLiveData<Pair<Double, Double>>()
    private val _markerVisibility = MutableLiveData<Boolean>()
    private val _npcMetadata = MutableLiveData<ArrayList<NpcItem>?>()
    private val _searchNpc = MutableLiveData<NpcItem?>()

    val scale: MutableLiveData<Float> get() = _scale
    val mapState: MutableLiveData<MapState> get() = _mapState
    val rotation: MutableLiveData<Float> get() = _rotation
    val floor: MutableLiveData<Int> get() = _floor
    val scroll: MutableLiveData<Pair<Double, Double>> get() = _scroll
    val markerVisibility: MutableLiveData<Boolean> get() = _markerVisibility
    val npcMetadata: MutableLiveData<ArrayList<NpcItem>?> get() = _npcMetadata
    val searchNpc: MutableLiveData<NpcItem?> get() = _searchNpc

    init {
        scale.value = 15.0F
        scroll.value = Pair(tibiaMaps.pixelInX(32369), tibiaMaps.pixelInY(32241))
        rotation.value = 0.0F
    }

    fun npcMetadata(){
        viewModelScope.launch {
            val npcMetadata = useCaseNpcMetadata.getNpcMetadata()
            _npcMetadata.value = npcMetadata
        }
    }

    fun searchNPC(name: String){
        viewModelScope.launch {
            _searchNpc.value = useCaseNpcMetadata.searchNPC(name)
        }
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