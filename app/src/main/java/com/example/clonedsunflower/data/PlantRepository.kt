package com.example.clonedsunflower.data

import com.example.clonedsunflower.data.PlantDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantRepository @Inject internal constructor(private val plantDao: PlantDao){
    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)
}