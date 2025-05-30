package com.example.languagelearningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagelearningapp.databinding.FragmentSelectConversastionalTopicBinding


class SelectConversastionalTopicFragment : Fragment() {

    private lateinit var binding: FragmentSelectConversastionalTopicBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSelectConversastionalTopicBinding.inflate(layoutInflater)

        setupClickListeners()
        return binding.root
    }



    private fun setupClickListeners() {
        binding.HomesandFamily.setOnClickListener { navigateToQuestions(1) }
        binding.shopping.setOnClickListener { navigateToQuestions(2) }
        // Add other topics...
    }



    private fun navigateToQuestions(topicId: Long) {
        val action = SelectConversastionalTopicFragmentDirections
            .actionSelectConversastionalTopicFragmentToBasicLearningFragment(topicId.toInt())
        findNavController().navigate(action)
    }
}