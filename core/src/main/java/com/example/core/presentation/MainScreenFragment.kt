package com.example.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.core.R
import com.example.core.databinding.FragmentMainscreenBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable

class MainScreenFragment : Fragment(){

    private lateinit var binding: FragmentMainscreenBinding

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

        return binding.root
    }
}