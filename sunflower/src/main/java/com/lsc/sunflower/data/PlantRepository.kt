package com.lsc.sunflower.data

/**
 * Created by lsc on 2020/4/20 10:20.
 * E-Mail:2965219926@qq.com
 */
class PlantRepository private constructor(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
            plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) =
                instance ?: synchronized(this) {
                    instance ?: PlantRepository(plantDao).also { instance = it }
                }
    }
}
