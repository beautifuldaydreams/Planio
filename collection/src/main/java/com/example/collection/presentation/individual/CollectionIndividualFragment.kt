package com.example.collection.presentation.individual

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.collection.R
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.collection.databinding.FragmentCollectionIndividualBinding
import com.example.collection.presentation.overview.CollectionOverviewViewModel
import com.example.storage.data.PlantPhoto
import java.io.File


class CollectionIndividualFragment: Fragment() {

    private lateinit var binding: FragmentCollectionIndividualBinding

    val context = this
    val debug3 = "DEBUG3"

    private val viewModel: CollectionOverviewViewModel by activityViewModels()
    var imgUrl : File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var imgPhoto: PlantPhoto? = null

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_collection_individual,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.toCollectionOverview.setOnClickListener {
            this.findNavController().navigate(
                CollectionIndividualFragmentDirections.actionCollectionIndividualFragmentToCollectionOverviewFragment()
            )
            viewModel.displayPlantDetailsComplete()
        }

        binding.button.text = viewModel.navigateToSelectedPlant.value?.plantName

        binding.collectionIndividualRecyclerview.adapter =
            CollectionIndividualAdapter((CollectionIndividualAdapter.OnClickListener {
                viewModel.displayPlantPhoto(it)
                imgPhoto = it
            }))

        binding.plantPhoto = viewModel.plantPhotoDisplay.value

        binding.collectionIndividualRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val myLayoutManager: LinearLayoutManager =
                    binding.collectionIndividualRecyclerview.layoutManager as LinearLayoutManager
                val scrollPosition = myLayoutManager.findFirstVisibleItemPosition()
                imgPhoto = viewModel.newPhotoList[scrollPosition]
                imgUrl = viewModel.newPhotoList[scrollPosition].plantFilePath
                imgUrl.let {
                    Glide.with(binding.collectionIndividualImageview)
                        .load(imgUrl)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_launcher_foreground)
                        )
                        .into(binding.collectionIndividualImageview)
                }
            }
        })
        binding.saveImage.setOnClickListener {
            val bit :Bitmap? = BitmapFactory.decodeFile(viewModel.plantPhotoDisplay.value?.plantFilePath.toString())
            viewModel.saveMediaToStorage(bit)
        }
        val deleteList = resources.getStringArray(R.array.delete_array)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, deleteList
        )

        val plantIndividual =
            CollectionIndividualFragmentArgs.fromBundle(requireArguments()).selectedPlant
        val plantIndividualName = plantIndividual.plantName
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if (position == 1) {
                    Log.i(debug3, "navigated?")
                    viewModel.deleteSelectedPlantIndividual(plantIndividual)
                    binding.spinner.setSelection(0)
                    context.findNavController().navigate(
                        CollectionIndividualFragmentDirections.actionCollectionIndividualFragmentToCollectionOverviewFragment()
                    )
                }
                if (position == 2) {
                    imgPhoto?.let { viewModel.deleteSelectedPlantPhoto(it) }
                    viewModel.retrievePlantList(plantIndividual)
                    viewModel.changeToPlantPhotos(viewModel.mediaPlantList)
                    binding.spinner.setSelection(0)
                    binding.collectionIndividualRecyclerview.adapter?.notifyDataSetChanged()
                    val num = viewModel.newPhotoList.size - 1
                    imgUrl = viewModel.newPhotoList[num].plantFilePath
                    imgUrl.let {
                        Glide.with(binding.collectionIndividualImageview)
                            .load(imgUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.loading_animation)
                                    .error(R.drawable.ic_launcher_foreground)
                            )
                            .into(binding.collectionIndividualImageview)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.button.text = plantIndividualName
        binding.viewModel = viewModel
        return binding.root
    }
}