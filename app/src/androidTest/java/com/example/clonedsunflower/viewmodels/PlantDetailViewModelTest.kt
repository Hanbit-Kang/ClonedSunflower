package com.example.clonedsunflower.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.clonedsunflower.MainCoroutineRule
import com.example.clonedsunflower.data.AppDatabase
import com.example.clonedsunflower.data.GardenPlantingRepository
import com.example.clonedsunflower.data.PlantRepository
import com.example.clonedsunflower.runBlockingTest
import com.example.clonedsunflower.utilities.getValue
import com.example.clonedsunflower.utilities.testPlant
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class PlantDetailViewModelTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: PlantDetailViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var plantRepository: PlantRepository

    @Inject
    lateinit var gardenPlantingRepository: GardenPlantingRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        val savedStateHandle = SavedStateHandle().apply {
            set("plantId", testPlant.plantId)
        }
        viewModel = PlantDetailViewModel(savedStateHandle, plantRepository, gardenPlantingRepository)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() = coroutineRule.runBlockingTest {
        assertFalse(getValue(viewModel.isPlanted))
    }
}