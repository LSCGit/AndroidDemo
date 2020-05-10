package com.lsc.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsc.sunflower.data.GardenPlantingRepository
import com.lsc.sunflower.data.PlantRepository
import kotlinx.coroutines.launch

/**
 * Created by lsc on 2020/4/20 10:19.
 * E-Mail:2965219926@qq.com
 */
class PlantDetailViewModel(
        plantRepository: PlantRepository,
        private val gardenPlantingRepository: GardenPlantingRepository,
        private val plantId: String
) : ViewModel() {

    val isPlanted = gardenPlantingRepository.isPlanted(plantId)
    val plant = plantRepository.getPlant(plantId)

    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }
}