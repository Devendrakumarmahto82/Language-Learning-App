package com.example.languagelearningapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.languagelearningapp.Authentication.Activity2
import com.example.languagelearningapp.databinding.FragmentAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class AccountFragment : Fragment() {

    private val auth = Firebase.auth
    private lateinit var sharedPreferences: SharedPreferences

    // SharedPreferences keys
    private companion object {
        const val PREFS_NAME = "SchoolPrefs"
        const val KEY_SCHOOL_NAME = "school_name"
        const val KEY_EMAIL = "email"
        const val KEY_PHONE = "phone"
        const val KEY_EST_DATE = "est_date"
        const val KEY_EDIT_MODE = "edit_mode"
    }

    private lateinit var binding: FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccountBinding.inflate(layoutInflater)

        binding.logOutBtn.setOnClickListener {
            auth.signOut()

            val intent = Intent(requireContext(), Activity2::class.java)
            startActivity(intent)
            requireActivity().finish()

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load saved data and set initial UI state
        loadSavedData()
        setEditMode(sharedPreferences.getBoolean(KEY_EDIT_MODE, false))

        // Set up button listeners
        setupButtonListeners()
    }

    private fun loadSavedData() {
        binding.schoolNameEditText.setText(sharedPreferences.getString(KEY_SCHOOL_NAME, ""))
        binding.email.setText(sharedPreferences.getString(KEY_EMAIL, ""))
        binding.phoneNumberEditText.setText(sharedPreferences.getString(KEY_PHONE, ""))
        binding.establishedDate.setText(sharedPreferences.getString(KEY_EST_DATE, ""))
    }

    private fun setupButtonListeners() {
        // Save Button
        binding.saveButton.setOnClickListener {
            saveSchoolInfo()
        }
        // Edit Button
        binding.editBtn.setOnClickListener {
            setEditMode(true)
        }
    }

    private fun saveSchoolInfo() {
        val schoolName = binding.schoolNameEditText.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val phone = binding.phoneNumberEditText.text.toString().trim()
        val estDate = binding.establishedDate.text.toString().trim()

        // Validate inputs
        if (schoolName.isEmpty() || email.isEmpty() || phone.isEmpty() || estDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidEmail(email)) {
            Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Save to SharedPreferences
        sharedPreferences.edit().apply {
            putString(KEY_SCHOOL_NAME, schoolName)
            putString(KEY_EMAIL, email)
            putString(KEY_PHONE, phone)
            putString(KEY_EST_DATE, estDate)
            putBoolean(KEY_EDIT_MODE, false) // Disable edit mode after saving
            apply()
        }

        // Update UI
        setEditMode(false)
        Toast.makeText(requireContext(), "Information saved!", Toast.LENGTH_SHORT).show()
    }

    private fun setEditMode(enabled: Boolean) {
        // Enable/disable input fields
        binding.schoolNameEditText.isEnabled = enabled
        binding.email.isEnabled = enabled
        binding.phoneNumberEditText.isEnabled = enabled
        binding.establishedDate.isEnabled = enabled

        // Save edit mode state
        sharedPreferences.edit().putBoolean(KEY_EDIT_MODE, enabled).apply()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun navigateToLogin() {
        // Clear any sensitive data
        sharedPreferences.edit().clear().apply()

        // Navigate to login screen
        val intent = Intent(requireContext(), Activity2::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        requireActivity().finish()
    }
}