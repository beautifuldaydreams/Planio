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

    private val viewModel: CollectionOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_individual, container, false)

        //Todo: find where I'm passing a bundle because I do not need a rootDirectory
        binding.toCollectionOverview.setOnClickListener {
            this.findNavController().navigate(CollectionIndividualFragmentDirections.
            actionCollectionIndividualFragmentToCollectionOverviewFragment())
        }

        binding.lifecycleOwner = this

        val plantIndividual = CollectionIndividualFragmentArgs.fromBundle(requireArguments()).selectedPlant
        val viewModelFactory = CollectionIndividualViewModelFactory(plantIndividual, )
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(CollectionIndividualViewModel::class.java)

        binding.collectionIndividualRecyclerview.adapter = CollectionIndividualAdapter()
        Log.d("DEBUG1", "Adapter initialized?")

        return binding.root
    }
}