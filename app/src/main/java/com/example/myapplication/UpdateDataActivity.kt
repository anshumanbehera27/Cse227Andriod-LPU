
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Employee
import com.example.myapplication.FatchDataActivity
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDataActivity : AppCompatActivity() {
    private lateinit var etEmplName: EditText
    private lateinit var etempID: TextView
    private lateinit var etEmplAge: EditText
    private lateinit var etEmplSalary: EditText
    private lateinit var btnUpdateData: Button
    private lateinit var dbref: DatabaseReference

    var name: String = ""
    var Age: Int = 0
    var salary: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        etEmplName = findViewById(R.id.tvEmpName)
      //  etempID = findViewById(R.id.tvEmpID)  // Initialize etempID here
        etEmplAge = findViewById(R.id.tvEmpAge)
        etEmplSalary = findViewById(R.id.tvEmpSalary)
        btnUpdateData = findViewById(R.id.btnUpdate)

        setValuesToViews()  // Set values from intent

        // Now use etempID after it's initialized and set
        dbref = FirebaseDatabase.getInstance().getReference("employees").child(etempID.text.toString())

        btnUpdateData.setOnClickListener {
            val emInfo = Employee(
                etEmplName.text.toString(),
                etEmplAge.text.toString().toInt(),
                etEmplSalary.text.toString().toInt()
            )
            val mTask = dbref.setValue(emInfo)

            mTask.addOnSuccessListener {
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, FatchDataActivity::class.java)
                finish()
                startActivity(intent)
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setValuesToViews() {
        etempID.text = intent.getStringExtra("id")
        etEmplName.setText(intent.getStringExtra("name"))
        etEmplAge.setText(intent.getStringExtra("age"))
        etEmplSalary.setText(intent.getStringExtra("salary"))

        name = etEmplName.text.toString()
        Age = etEmplAge.text.toString().toInt()
        salary = etEmplSalary.text.toString().toInt()
    }
}
