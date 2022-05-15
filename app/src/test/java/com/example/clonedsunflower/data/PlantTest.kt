package com.example.clonedsunflower.data

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.Calendar.DAY_OF_YEAR

class PlantTest {
    private lateinit var plant: Plant

    @Before fun setUp() {
        plant = Plant("1", "Apple", "Hi", 1, 2, "")
    }

    @Test fun test_default_values() {
        val defaultPlant = Plant("2", "Banana", "Desc", 1)
        assertEquals(7, defaultPlant.wateringInterval)
        assertEquals("", defaultPlant.imageUrl)
    }

    @Test fun test_shouldBeWatered() {
        Calendar.getInstance().let{ now ->
            val lastWateringDate = Calendar.getInstance()

            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -0) }))

            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -1) }))

            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -2) }))

            lastWateringDate.time = now.time
            assertTrue(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -3) }))
        }
    }

    @Test fun test_toString() {
        assertEquals("Apple", plant.toString())
    }
}