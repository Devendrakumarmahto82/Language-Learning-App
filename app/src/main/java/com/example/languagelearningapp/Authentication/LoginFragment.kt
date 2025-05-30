package com.example.languagelearningapp.Authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.Home.HomeActivity
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private val viewModel: AuthViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentLoginBinding.inflate(layoutInflater)

      onLoginBtnClicked()
        return binding.root
    }

    private fun onLoginBtnClicked() {
        binding.loginbtn.setOnClickListener {
           // findNavController().navigate(R.id.action_loginFragment_to_displaySuccesfullyFragment)
            logInUser()
        }
    }
    private fun logInUser() {
        val email=binding.schoolId.text.toString()
        val password=binding.schoolPassword.text.toString()
        if(!validateInput(email,password)) return

        showLoading(true)
        viewModel.loginUser(email,password){success,message->
            requireActivity().runOnUiThread {
                showLoading(false)
                if (success) {
                    startMainActivity()

                    // Clear the back stack
                    requireActivity().finishAffinity()
                } else {
                    binding.tvError.text = message
                }
            }
        }

    }

    private fun startMainActivity() {
        val intent= Intent(requireContext(),HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility= if(show) View.VISIBLE else View.GONE
        binding.loginbtn.isEnabled= !show

    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(requireContext(),"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           Toast.makeText(requireContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length<6){
          Toast.makeText(requireContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}