package com.example.clonedsunflower.data

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class GardenPlantingTest {
    @Test
    fun testDefaultValues() {
        val gardenPlanting = GardenPlanting("1")
        val cal = Calendar.getInstance()
        assertYMD(cal, gardenPlanting.plantDate)
        assertYMD(cal, gardenPlanting.lastWateringDate)
        assertEquals(0L, gardenPlanting.gardenPlantingId)
    }

    private fun assertYMD(expectedCal: Calendar, actualCal: Calendar) {
        assertEquals(actualCal.get(YEAR), expectedCal.get(YEAR))
        assertEquals(actualCal.get(MONTH), expectedCal.get(MONTH))
        assertEquals(actualCal.get(DAY_OF_MONTH), expectedCal.get(DAY_OF_MONTH))
    }
}