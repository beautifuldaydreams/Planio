package com.example.collection.presentation.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.collection.R
import com.example.collection.RTAG
import com.example.collection.databinding.FragmentCollectionOverviewBinding
import com.example.collection.presentation.popup.PopupFragment
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable

val EXTENSION_WHITELIST = arrayOf("JPG")

class CollectionOverviewFragment: Fragment(), PopupFragment.OnInputSelected{

    private val TAG = "CollectionOverviewFragment"

    private lateinit var binding: FragmentCollectionOverviewBinding
    private lateinit var dialog: PopupFragment
    private val viewModel: CollectionOverviewViewModel by activityViewModels()

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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_collection_overview,
            container,
            false
        )

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        binding.overviewRecyclerView.adapter = CollectionOverviewAdapter(CollectionOverviewAdapter.OnClickListener{
            viewModel.displayPlantDetails(it)
        })
        Log.d(RTAG, "Adapter initialized?")


        viewModel.navigateToSelectedPlant.observe(viewLifecycleOwner, {
            if (null != it) {

                viewModel.retrievePlantList(it)
                viewModel.changeToPlantPhotos(viewModel.mediaPlantList)

                Log.d("DEBUG1", "listPlantPhoto in OverviewFragment is empty? " + viewModel.listPlantPhoto.value?.isEmpty())

                this.findNavController().navigate(
                    CollectionOverviewFragmentDirections.actionCollectionOverviewFragmentToCollectionIndividualFragment(it))
            }
        })

//        viewModel.newListLiveData.observe(viewLifecycleOwner, {
//            binding.overviewRecyclerView.adapter.submitList2(it)
//        })

        binding.addPlant.setOnClickListener {
            dialog = PopupFragment()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager, "popupFragment")
        }

        binding.toMainScreen.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.HomeFlow)
        }

        return binding.root
    }

    override fun sendInput(input: String) {
            viewModel.makeNewPlant(input)
    }
}