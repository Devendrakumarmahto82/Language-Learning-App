package com.example.languagelearningapp.Authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.languagelearningapp.Home.HomeActivity
import com.example.languagelearningapp.databinding.FragmentIntroPage2Binding

class IntroPage2Fragment : Fragment() {

    private lateinit var binding:FragmentIntroPage2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding= FragmentIntroPage2Binding.inflate(layoutInflater)

        binding.linearLayout.setOnClickListener {
            val intent= Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}