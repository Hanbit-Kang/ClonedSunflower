package com.example.clonedsunflower.utilities

import com.example.clonedsunflower.data.GardenPlanting
import com.example.clonedsunflower.data.Plant
import java.util.*

val testPlants = arrayListOf(
    Plant("1", "Apple", "A red fruit", 1),
    Plant("2", "B", "Description B", 1),
    Plant("3", "C", "Description C", 2)
)
val testPlant = testPlants[0]

val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1998)
    set(Calendar.MONTH, Calendar.SEPTEMBER)
    set(Calendar.DAY_OF_MONTH, 4)
}

val testGardenPlanting = GardenPlanting(testPlant.plantId, testCalendar, testCalendar)

