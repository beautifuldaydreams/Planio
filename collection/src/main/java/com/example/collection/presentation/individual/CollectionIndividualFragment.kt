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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.collection.RTAG
import com.example.collection.databinding.FragmentCollectionIndividualBinding
import com.example.collection.presentation.overview.CollectionOverviewAdapter
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

        binding.collectionIndividualRecyclerview.adapter = CollectionIndividualAdapter((CollectionIndividualAdapter.OnClickListener{
            viewModel.displayPlantPhoto(it)
        }))

        binding.plantPhoto = viewModel.plantPhotoDisplay.value

        binding.collectionIndividualRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val myLayoutManager: LinearLayoutManager  = binding.collectionIndividualRecyclerview.layoutManager as LinearLayoutManager
                val scrollPosition = myLayoutManager.findFirstVisibleItemPosition()

                val imgUrl = viewModel.newPhotoList[scrollPosition].plantFilePath
                imgUrl.let {
                    Glide.with(binding.collectionIndividualImageview)
                        .load(imgUrl)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image))
                        .into(binding.collectionIndividualImageview)
                }
            }
        })

        binding.viewModel = viewModel
        return binding.root
    }
}