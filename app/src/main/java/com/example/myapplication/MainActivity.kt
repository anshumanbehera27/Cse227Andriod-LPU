package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    /*
     TODO how to login with google
     1 - add go to fire base setting enable google and add SHA to the project setting
     2-  go to greadel and serach fro greadel report and  past thr sha
     3 - add all the dependecy
     4 -
     */

   private lateinit var auth:FirebaseAuth
   lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnsignout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this , loginpage::class.java))
            finish()
        }



    }
}