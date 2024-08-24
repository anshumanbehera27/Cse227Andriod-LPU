package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FatchDataActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingData: TextView
    private lateinit var dbref: DatabaseReference
    private lateinit var empList: MutableList<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fatch_data)

        // Initialize views
        recyclerView = findViewById(R.id.recycleViewFatch)
        loadingData = findViewById(R.id.tvloding)
        empList = arrayListOf()

        // Initialize Firebase Database reference
        dbref = FirebaseDatabase.getInstance().getReference("employees")

        // Set up RecyclerView with a layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Fetch data from Firebase
        getEmpData()
    }

    private fun getEmpData() {
        // Show loading indicator and hide RecyclerView initially
        recyclerView.visibility = View.GONE
        loadingData.visibility = View.VISIBLE

        // Attach a listener to read the data at our "employees" reference
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()) {
                    for (empsnap in snapshot.children) {
                        // Fetch data for each employee
                        val empName = empsnap.child("name").getValue(String::class.java) ?: ""
                        val empSalary = empsnap.child("salary").getValue(Int::class.java) ?: 0
                        val empAge = empsnap.child("age").getValue(Int::class.java) ?: 0

                        // Create Employee object and add to the list
                        val employee = Employee(empName, empSalary, empAge)
                        empList.add(employee)
                    }

                    // Initialize and set the adapter
                    val mAdapter = EmpAdapter(empList)
                    recyclerView.adapter = mAdapter

                    // Show RecyclerView and hide loading indicator
                    recyclerView.visibility = View.VISIBLE
                    loadingData.visibility = View.GONE
                } else {
                    // Show a message if no data exists
                    loadingData.text = "No data available"
                    loadingData.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle the error, for example, by showing an error message
                loadingData.text = "Failed to load data: ${error.message}"
                loadingData.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        })
    }
}
