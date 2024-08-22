package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class pdfUploadActivity : AppCompatActivity() {

    private val PICK_PDF_REQUEST = 1
    private lateinit var buttonChooseFile: Button
    private lateinit var buttonUploadFile: Button

    private var pdfUri: Uri? = null
    private lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_upload)

        buttonChooseFile = findViewById(R.id.button_choose_file)
        buttonUploadFile = findViewById(R.id.button_upload_file)

        storageReference = FirebaseStorage.getInstance().reference.child("pdfs")

        buttonChooseFile.setOnClickListener {
            openFileChooser()
        }

        buttonUploadFile.setOnClickListener {
            uploadFile()
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST)
    }

    private fun uploadFile() {
        if (pdfUri != null) {
            val fileReference = storageReference.child("${System.currentTimeMillis()}.pdf")
            fileReference.putFile(pdfUri!!)
                .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                })
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }


}
