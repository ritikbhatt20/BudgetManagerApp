package com.example.budgetmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.example.budgetmanagerapp.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val email = binding.etMail.text.toString()
            val pass = binding.etPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                        val intent =Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}