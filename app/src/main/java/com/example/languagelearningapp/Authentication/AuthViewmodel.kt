package com.example.languagelearningapp.Authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class AuthViewmodel:ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val database= Firebase.database.reference


    fun registerUser(email:String,password: String ,callback: (Boolean, String) -> Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val userId=auth.currentUser?.uid?:""
                    // save user to Realtime Database
                    database.child("User").child(userId).child("emailId").child("email").setValue(email)
                    callback(true,"Registration successful!")
                } else{
                    callback(false,task.exception?.message?:"Registration failed")
                }
            }
    }


    fun loginUser(emailId:String,password:String,callback:(Boolean,String)-> Unit){
        auth.signInWithEmailAndPassword(emailId,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    callback(true,"Login Successful!")
                } else{
                    callback(false,task.exception?.message?:"Login Failed")
                }
            }
    }

    fun isUserLoggedIn():Boolean= auth.currentUser!=null
}