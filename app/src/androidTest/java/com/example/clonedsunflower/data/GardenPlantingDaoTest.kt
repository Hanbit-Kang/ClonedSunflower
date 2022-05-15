package com.example.clonedsunflower.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.clonedsunflower.utilities.testCalendar
import com.example.clonedsunflower.utilities.testGardenPlanting
import com.example.clonedsunflower.utilities.testPlant
import com.example.clonedsunflower.utilities.testPlants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GardenPlantingDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var gardenPlantingDao: GardenPlantingDao
    private var testGardenPlantingId = 0L

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        gardenPlantingDao = database.gardenPlantingDao()

        database.plantDao().insertAll(testPlants)
        testGardenPlantingId = gardenPlantingDao.insertGardenPlanting(testGardenPlanting)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetGardenPlantings() = runBlocking {
        val gardenPlanting2 = GardenPlanting(
            testPlants[1].plantId,
            testCalendar,
            testCalendar
        ).also { it.gardenPlantingId = 2 }
        gardenPlantingDao.insertGardenPlanting(gardenPlanting2)
        assertThat(gardenPlantingDao.getGardenPlantings().first().size, equalTo(2))
    }

    @Test
    fun testDeleteGardenPlanting() = runBlocking {
        val gardenPlanting2 = GardenPlanting(
            testPlants[1].plantId,
            testCalendar,
            testCalendar
        ).also { it.gardenPlantingId = 2 }
        gardenPlantingDao.insertGardenPlanting(gardenPlanting2)
        assertThat(gardenPlantingDao.getGardenPlantings().first().size, equalTo(2))
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting2)
        assertThat(gardenPlantingDao.getGardenPlantings().first().size, equalTo(1))
    }

    @Test
    fun testGetGardenPlantingForPlant() = runBlocking {
        assertTrue(gardenPlantingDao.isPlanted(testPlant.plantId).first())
    }

    @Test
    fun testGetGardenPlantingForPlant_notFound() = runBlocking {
        assertFalse(gardenPlantingDao.isPlanted(testPlants[2].plantId).first())
    }

    @Test
    fun testGetPlantAndGardenPlantings() = runBlocking {
        val plantAndGardenPlantings = gardenPlantingDao.getPlantedGardens().first()
        assertThat(plantAndGardenPlantings.size, equalTo(1))

        assertThat(plantAndGardenPlantings[0].plant, equalTo(testPlant))
        assertThat(plantAndGardenPlantings[0].gardenPlantings.size, equalTo(1))
        assertThat(plantAndGardenPlantings[0].gardenPlantings[0], equalTo(testGardenPlanting))
    }
}