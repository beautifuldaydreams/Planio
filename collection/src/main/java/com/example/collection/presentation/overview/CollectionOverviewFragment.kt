package com.example.collection.presentation.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.collection.R
import com.example.collection.databinding.FragmentCollectionOverviewBinding
import com.example.collection.presentation.popup.PopupFragment
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable

class CollectionOverviewFragment: Fragment(), PopupFragment.OnInputSelected{

    private lateinit var binding: FragmentCollectionOverviewBinding
    private lateinit var dialog: PopupFragment
    private val viewModel: CollectionOverviewViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.overviewRecyclerView.adapter = CollectionOverviewAdapter(CollectionOverviewAdapter.OnClickListener{
            viewModel.displayPlantDetails(it)
        })
        viewModel.navigateToSelectedPlant.observe(viewLifecycleOwner, {
            if (null != it) {

                viewModel.retrievePlantList(it)
                viewModel.changeToPlantPhotos(viewModel.mediaPlantList)

                this.findNavController().navigate(
                    CollectionOverviewFragmentDirections.actionCollectionOverviewFragmentToCollectionIndividualFragment(it))
            }
        })
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
        binding.overviewRecyclerView.adapter?.notifyDataSetChanged()
    }
}