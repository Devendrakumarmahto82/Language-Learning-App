package com.example.languagelearningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.databinding.FragmentSelectLevelBinding


class SelectLevelFragment : Fragment() {

    private lateinit var binding:FragmentSelectLevelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentSelectLevelBinding.inflate(layoutInflater)

        binding.cardView1.setOnClickListener {
            findNavController().navigate(R.id.action_selectLevelFragment_to_selectConversastionalTopicFragment)
        }

        binding.cardView2.setOnClickListener {
            findNavController().navigate(R.id.action_selectLevelFragment_to_selectConversastionalTopicFragment)
        }

        binding.cardView3.setOnClickListener {
            findNavController().navigate(R.id.action_selectLevelFragment_to_selectConversastionalTopicFragment)
        }

       return binding.root
    }


}