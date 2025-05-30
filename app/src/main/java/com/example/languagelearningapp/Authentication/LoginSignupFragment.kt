package com.example.languagelearningapp.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.FragmentLoginSignupBinding


class LoginSignupFragment : Fragment() {

    private lateinit var binding:FragmentLoginSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentLoginSignupBinding.inflate(inflater,container,false)

        goToLoginFragment()
        goToSignupFragment()
        return binding.root
    }

    private fun goToSignupFragment() {
        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginSignupFragment_to_signupFragment)
        }
    }

    private fun goToLoginFragment() {
       binding.loginButton.setOnClickListener {
           findNavController().navigate(R.id.action_loginSignupFragment_to_loginFragment)
       }
    }

}