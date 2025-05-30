package com.example.languagelearningapp.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.FragmentIntorPage1Binding

class IntorPage1Fragment : Fragment() {


    private lateinit var binding:FragmentIntorPage1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentIntorPage1Binding.inflate(layoutInflater,container,false)

        binding.nextBtnLayout.setOnClickListener {
            findNavController().navigate(R.id.action_intorPage1Fragment_to_introPage2Fragment)

        }
        return binding.root
    }


}