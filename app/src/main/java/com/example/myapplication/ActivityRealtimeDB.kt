package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityRealtimeDB : AppCompatActivity() {
   lateinit var etname :EditText
   lateinit var etage:EditText
   lateinit var etsalary:EditText
   lateinit var btninsert: Button
   lateinit var btnfatch: Button
   lateinit var dbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_db)

        etname = findViewById(R.id.etname)
        etage = findViewById(R.id.etage)
        etsalary = findViewById(R.id.etsalary)
        btninsert = findViewById(R.id.btninsert)
        btnfatch = findViewById(R.id.btnfatch)

        btninsert.setOnClickListener {
            val name = etname.text.toString()
            val age = etage.text.toString()
            val salary = etsalary.text.toString()
            if (name.isBlank() || age.isBlank() || salary.isBlank()) {
                Toast.makeText(this, "enter the data ", Toast.LENGTH_SHORT).show()
            }
            dbref = FirebaseDatabase.getInstance().getReference("employees")
            val empid = dbref.push().key!!

            val employee = Employee(name , age.toInt() , salary.toInt())

            dbref.child(empid).setValue(employee)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()

                    etname.text.clear()
                    etsalary.text.clear()
                    etage.text.clear()


                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()

                }


        }
        btnfatch.setOnClickListener{
            // how to fatch the data from firebase
            val empid = etname.text.toString()
            if (empid.isNotEmpty()){

            }



        }


    }
}