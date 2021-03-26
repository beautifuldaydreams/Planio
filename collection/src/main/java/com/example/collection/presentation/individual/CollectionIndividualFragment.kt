package com.example.collection.presentation.individual

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collection.R
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.collection.RTAG
import com.example.collection.databinding.FragmentCollectionIndividualBinding
import com.example.collection.presentation.overview.CollectionOverviewViewModel


class CollectionIndividualFragment: Fragment() {

    private lateinit var binding: FragmentCollectionIndividualBinding

    private val viewModel: CollectionOverviewViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_individual, container, false)
        binding.lifecycleOwner = this

        //Todo: find where I'm passing a bundle because I do not need a rootDirectory
        binding.toCollectionOverview.setOnClickListener {
            this.findNavController().navigate(CollectionIndividualFragmentDirections.
            actionCollectionIndividualFragmentToCollectionOverviewFragment())
            viewModel.displayPlantDetailsComplete()
        }

        var plantIndividual = CollectionIndividualFragmentArgs.fromBundle(requireArguments()).selectedPlant

        binding.testIndividual.text = plantIndividual.plantName

        binding.collectionIndividualRecyclerview.adapter = CollectionIndividualAdapter()

        binding.plantPhoto = viewModel.plantPhotoDisplay.value

        binding.viewModel = viewModel

        return binding.root
    }
}