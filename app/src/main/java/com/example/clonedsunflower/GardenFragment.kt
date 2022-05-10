package com.example.clonedsunflower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.clonedsunflower.adapters.GardenPlantingAdapter
import com.example.clonedsunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.example.clonedsunflower.databinding.FragmentGardenBinding
import com.example.clonedsunflower.viewmodels.GardenPlantingListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {
    private lateinit var binding: FragmentGardenBinding
    private val viewModel: GardenPlantingListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding){
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) {
            binding.hasPlantings = !it.isNullOrEmpty()
            adapter.submitList(it)
        }
    }

    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}