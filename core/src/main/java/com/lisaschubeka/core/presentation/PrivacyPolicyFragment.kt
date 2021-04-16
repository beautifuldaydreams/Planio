package com.lisaschubeka.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lisaschubeka.core.R
import com.lisaschubeka.core.databinding.FragmentPrivacypolicyBinding

class PrivacyPolicyFragment: Fragment() {

    private lateinit var binding: FragmentPrivacypolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacypolicy, container, false)
        binding.imageButton2.setOnClickListener {
            this.findNavController().navigate(R.id.action_privacyPolicyFragment_to_homeFragment)
        }

        return binding.root
    }

}