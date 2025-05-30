package com.example.languagelearningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.databinding.FragmentSeeLanguageBinding

class SeeLanguageFragment : Fragment() {

    private lateinit var binding:FragmentSeeLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentSeeLanguageBinding.inflate(layoutInflater)


        binding.language1.setOnClickListener {
            findNavController().navigate(R.id.action_seeLanguageFragment_to_selectLevelFragment)
        }


        binding.language2.setOnClickListener {
            findNavController().navigate(R.id.action_seeLanguageFragment_to_selectLevelFragment)
        }

        binding.language3.setOnClickListener {
            findNavController().navigate(R.id.action_seeLanguageFragment_to_selectLevelFragment)
        }

        binding.language4.setOnClickListener {
            findNavController().navigate(R.id.action_seeLanguageFragment_to_selectLevelFragment)
        }

        binding.language5.setOnClickListener {
            findNavController().navigate(R.id.action_seeLanguageFragment_to_selectLevelFragment)
        }
        return binding.root
    }


}