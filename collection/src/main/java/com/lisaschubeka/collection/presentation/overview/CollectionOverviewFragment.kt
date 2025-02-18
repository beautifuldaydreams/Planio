package com.lisaschubeka.collection.presentation.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lisaschubeka.collection.R
import com.lisaschubeka.collection.databinding.FragmentCollectionOverviewBinding
import com.lisaschubeka.collection.presentation.popup.PopupFragment
import com.lisaschubeka.navigation.NavigationFlow
import com.lisaschubeka.navigation.ToFlowNavigatable

class CollectionOverviewFragment: Fragment(), PopupFragment.OnInputSelected{

    private lateinit var binding: FragmentCollectionOverviewBinding
    private lateinit var dialog: PopupFragment
    private val viewModel: CollectionOverviewViewModel by activityViewModels()
    private lateinit var safeContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
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