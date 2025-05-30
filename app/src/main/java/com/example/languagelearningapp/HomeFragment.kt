package com.example.languagelearningapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.databinding.FragmentHomeBinding
import com.example.languagelearningapp.flashcard.MainActivity

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentHomeBinding.inflate(layoutInflater)

        binding.langCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seeLanguageFragment)
        }

        binding.account.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
        }
        binding.searchCardView.setOnClickListener {
            val intent=Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        binding.engCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectConversastionalTopicFragment)
        }

        binding.upcomingCourse1.setOnClickListener {
            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_SHORT).show()
        }

        binding.upcomingCourse2.setOnClickListener {
            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }


}