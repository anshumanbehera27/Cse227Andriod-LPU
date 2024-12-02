package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class signUp : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.btsignup.setOnClickListener{

            val email =  binding.etemail.text.toString()
            val password = binding.etpassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){

                auth.createUserWithEmailAndPassword(email , password)
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful){

                            Toast.makeText(this , "Sign up is successful" , Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this , MainActivity::class.java))
                            finish()

                        }
                        else{
                           // Toast.makeText(this , "Sign up is not successful" , Toast.LENGTH_SHORT).show()
                            Toast.makeText(this , task.exception.toString() , Toast.LENGTH_SHORT).show()

                        }

                    }
            }
            else{
                Toast.makeText(this , "Enter the email and password" , Toast.LENGTH_SHORT).show()
            }

        }

    }
}