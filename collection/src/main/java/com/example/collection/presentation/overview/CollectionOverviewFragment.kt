package com.example.collection.presentation.overview

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.collection.R
import com.example.collection.RTAG
import com.example.collection.databinding.FragmentCollectionOverviewBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import java.io.File
import java.util.*

val EXTENSION_WHITELIST = arrayOf("JPG")

class CollectionOverviewFragment: Fragment(){


    private lateinit var binding: FragmentCollectionOverviewBinding
    private val viewModel: CollectionOverviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("OnCreate", "passed superOncreate")
        // Mark this as a retain fragment, so the lifecycle does not get restarted on config change
        retainInstance = true
        Log.i("OnCreate", "context: " + context.toString())
//        viewModel.retrieveFileList()
//        viewModel.changeToPlantPhotos(viewModel.mediaLists)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_overview, container, false)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        binding.overviewRecyclerView.adapter = CollectionOverviewAdapter()
        Log.d(RTAG, "Adapter initialized?")

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

//        Glide.with(this)
//            .load(viewModel.getImgFromPlantIndividual(viewModel.mediaLists.last()))
//            .into(binding.imageView2)
//        Log.i("OnCreate", "Glide successful")