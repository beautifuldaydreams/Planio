package com.example.collection.presentation.overview

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.collection.R
import com.example.collection.databinding.FragmentCollectionOverviewBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import java.io.File
import java.util.*

val EXTENSION_WHITELIST = arrayOf("JPG")

class CollectionOverviewFragment: Fragment(){

    private lateinit var binding: FragmentCollectionOverviewBinding
//    private lateinit var viewModel: CollectionOverviewViewModel
//    private lateinit var viewModelFactory: CollectionOverviewViewModelFactory

    private lateinit var mediaLists: MutableList<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("OnCreate", "passed superOncreate")

        // Mark this as a retain fragment, so the lifecycle does not get restarted on config change
        retainInstance = true

        Log.i("OnCreate", "context: " + context.toString())

        mediaLists = context?.getExternalFilesDir("Planio")?.listFiles { file ->
            EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()
        Log.i("OnCreate", "mediaList created")
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("jpg").toString())
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("JPG").toString())
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("media").toString())

        Log.i("OnCreate", "mediaLists size: " + mediaLists.size.toString())
        
        for (item in mediaLists) {
            Log.i("OnCreate", println(item.absoluteFile).toString())
            Log.i("OnCreate", println(item.isFile).toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_overview, container, false)

        val application = requireNotNull(this.activity).application

//        viewModelFactory = CollectionOverviewViewModelFactory(CollectionOverviewFragmentArgs.fromBundle(requireArguments()).rootDirectory)
//        viewModel = ViewModelProvider(this, viewModelFactory)
//            .get(CollectionOverviewViewModel::class.java)
//        Log.i("OnCreateView", "viewModel created via Factory")

        binding.toCollectionIndividual.setOnClickListener {
            findNavController().navigate(
                CollectionOverviewFragmentDirections.
                actionCollectionOverviewFragmentToCollectionIndividualFragment())
        }

        binding.toMainScreen.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.HomeFlow)
        }

        binding.setLifecycleOwner(this)

        Glide.with(this)
            .load(mediaLists[0])
            .into(binding.imageView2)
        Log.i("OnCreate", "Glide successful")

        return binding.root
    }
}