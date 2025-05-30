package com.example.languagelearningapp.Authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.Home.HomeActivity
import com.example.languagelearningapp.R


class SplashScreenFragment : Fragment() {

    private val viewModel: AuthViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            Handler(Looper.getMainLooper()).postDelayed({



                if (viewModel.isUserLoggedIn()){
                    val intent= Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)

                    requireActivity().finish()
                }
                else{
                    // this is for navigating to another fragment
                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginSignupFragment)
                }
            },3000)


    }
}