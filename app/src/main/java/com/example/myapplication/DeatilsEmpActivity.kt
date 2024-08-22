package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeatilsEmpActivity : AppCompatActivity() {
    lateinit var dbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deatils_emp)

        val empName = intent.getStringExtra("name")
        val empAge = intent.getIntExtra("age", 0)
        val empSalary = intent.getIntExtra("salary", 0)

        val tvname: TextView = findViewById(R.id.tvEmpName)
        val tvAge: TextView = findViewById(R.id.tvEmpAge)
        val tvSalary: TextView = findViewById(R.id.tvEmpSalary)
        val UpdateButton: Button = findViewById(R.id.btnUpdate)
        val DeleteButton: Button = findViewById(R.id.btnDelete)


      if (tvname != null && tvAge != null && tvSalary != null) {
          tvname.text = empName
          tvAge.text = empAge.toString()
          tvSalary.text = empSalary.toString()
      }
        else{
            Toast.makeText(this , "Fatch All Your data" , Toast.LENGTH_SHORT).show()
      }

        // Apply  How to Update The data
        UpdateButton.setOnClickListener {

            Toast.makeText(this , "Update The Data" , Toast.LENGTH_SHORT).show()
        }

        // Apply how to delete the data
        DeleteButton.setOnClickListener {
            Toast.makeText(this , "Delete The Data" , Toast.LENGTH_SHORT).show()
        }


    }
    private fun updateEmpData(tvname: TextView, tvAge: TextView, tvSalary: TextView){
        dbref = FirebaseDatabase.getInstance().getReference().child("employees")
        val name = tvname.text.toString()
        val age = tvAge.text.toString().toInt()
        val salary = tvSalary.text.toString().toInt()
        val emp = mapOf<String , Any>(
            "name" to name,
            "age" to age,
            "salary" to salary
        )

        dbref.child(name).updateChildren(emp).addOnCompleteListener {
            Toast.makeText(this , "Data Updated" , Toast.LENGTH_SHORT).show()
        }
    }
}