package com.lisaschubeka.core.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lisaschubeka.core.R
import com.lisaschubeka.core.databinding.FragmentMainscreenBinding
import com.lisaschubeka.navigation.MainNavGraphDirections
import com.lisaschubeka.navigation.NavigationFlow
import com.lisaschubeka.navigation.ToFlowNavigatable


class MainScreenFragment : Fragment(){

    private lateinit var binding: FragmentMainscreenBinding
    private lateinit var safeContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.actionBar?.hide()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mainscreen, container, false)

        binding.toCamera.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.CameraFlow)
        }
        binding.toCollections.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.CollectionFlow)
        }

        binding.imageButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_privacyPolicyFragment)
   }

        return binding.root
    }
}