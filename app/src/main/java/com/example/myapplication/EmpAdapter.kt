package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for handling a list of Employee objects in a RecyclerView
class EmpAdapter(private val empList: MutableList<Employee>) :
    RecyclerView.Adapter<EmpAdapter.ViewHolder>() {

    // Listener for item click events
    private var mListener: OnItemClickListener? = null

    // Interface to handle click events on items
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Method to set the click listener from outside the adapter
    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for each RecyclerView item
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)
        // Return a new ViewHolder instance, passing the inflated view and the click listener
        return ViewHolder(itemView)
    }

    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the current Employee object from the list
        val currentEmp = empList[position]
        // Bind the employee name to the TextView in the ViewHolder
        holder.tvEmpName.text = currentEmp.name

        // Set the item click listener to start DetailsEmpActivity with Employee data
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DeatilsEmpActivity::class.java)
            intent.putExtra("name", currentEmp.name)
            intent.putExtra("salary", currentEmp.salary)
            intent.putExtra("age", currentEmp.Age)
            context.startActivity(intent)
        }
    }

    // Returns the total number of items in the RecyclerView
    override fun getItemCount(): Int {
        return empList.size
    }

    // ViewHolder class to hold the layout for each RecyclerView item
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TextView to display the employee name
        val tvEmpName: TextView = itemView.findViewById(R.id.tvempname)
    }
}
