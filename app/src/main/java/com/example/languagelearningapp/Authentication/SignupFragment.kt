package com.example.languagelearningapp.Authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.FragmentSignupBinding


class SignupFragment : Fragment() {

private lateinit var binding:FragmentSignupBinding
private val viewModel :AuthViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentSignupBinding.inflate(layoutInflater,)

        onSingupBtnClicked()
        return binding.root
    }

    private fun onSingupBtnClicked() {
        binding.singupBtn.setOnClickListener {
//            findNavController().navigate(R.id.action_signupFragment_to_displaySuccesfullyFragment)
            registerUser()
        }
    }

    private fun registerUser() {
        val email=binding.etMail.text.toString().trim()
        val password=binding.etPasswordtext.text.toString().trim()
        val confirmPassword=binding.etConfirmPassword.text.toString().trim()

        if(!validateInput(email,password,confirmPassword)) return

        showLoading(true)

        viewModel.registerUser(email,password){ success,message->
            showLoading(false)
            if (success){
                //  startActivity(Intent(requireActivity(),MainActivity::class.java))

                findNavController().navigate(R.id.action_signupFragment_to_displaySuccesfullyFragment)
            }
            else{
                binding.tvError.text=message
            }
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {

        if (email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
            Toast.makeText(requireContext(),"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           Toast.makeText(requireContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length<6){
           Toast.makeText(requireContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show()
        }
        if (password != confirmPassword){
            Toast.makeText(requireContext(),"Password does not match",Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    fun showLoading(show:Boolean){
        binding.progressBar.visibility= if (show) View.VISIBLE else View.GONE
        binding.singupBtn.isEnabled= !show
    }
}