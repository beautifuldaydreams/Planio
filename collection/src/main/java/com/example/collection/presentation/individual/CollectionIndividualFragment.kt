package com.example.collection.presentation.individual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collection.R
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.collection.databinding.FragmentCollectionIndividualBinding


class CollectionIndividualFragment: Fragment() {

    private lateinit var binding: FragmentCollectionIndividualBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_individual, container, false)

        binding.toCollectionOverview.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}