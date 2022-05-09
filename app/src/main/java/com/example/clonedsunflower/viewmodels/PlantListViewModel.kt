package com.example.clonedsunflower.viewmodels

import androidx.lifecycle.*
import com.example.clonedsunflower.data.Plant
import com.example.clonedsunflower.data.PlantDao
import com.example.clonedsunflower.data.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class PlantListViewModel @Inject internal constructor(
    plantRepository: PlantRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val growZone: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY) ?: NO_GROW_ZONE
    )

    val plants: LiveData<List<Plant>> = growZone.flatMapLatest {
        if(it == NO_GROW_ZONE){
            plantRepository.getPlants()
        }else{
            plantRepository.getPlantsWithGrowZoneNumber(it)
        }
    }.asLiveData()

    init {
        viewModelScope.launch {
            growZone.collect {
                savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, it)
            }
        }
    }

    fun setGrowZoneNumber(number: Int) {
        growZone.value = number
    }

    fun clearGrowZoneNumber() {
        growZone.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZone.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }
}