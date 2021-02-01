package com.example.collection.presentation.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.collection.R
import com.example.collection.databinding.FragmentCollectionOverviewBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable

class CollectionOverviewFragment: Fragment(){

    private lateinit var binding: FragmentCollectionOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_overview, container, false)

        binding.toCollectionIndividual.setOnClickListener {
            findNavController().navigate(
                CollectionOverviewFragmentDirections.
                actionCollectionOverviewFragmentToCollectionIndividualFragment())
        }

        binding.toMainScreen.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.HomeFlow)
        }

        return binding.root
    }
}