package com.example.budgetmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.budgetmanagerapp.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignIn.setOnClickListener {
            val email = binding.etMail.text.toString()
            val pass = binding.etPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent =Intent(this, BudgetActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "User does not exist, please Sign Up", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        val btnCreate = findViewById<Button>(R.id.btnCreate)
        btnCreate.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}