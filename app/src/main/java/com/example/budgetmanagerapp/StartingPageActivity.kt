package com.example.budgetmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.imageview.ShapeableImageView

class StartingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_page)

        val imageView= findViewById<ShapeableImageView>(R.id.imageView3)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)

        btnRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        btnSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}