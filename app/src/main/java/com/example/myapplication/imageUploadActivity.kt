package com.example.myapplication

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityImageUploadBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class imageUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityImageUploadBinding
    lateinit var imageView: ImageView
    lateinit var getContent: ActivityResultLauncher<String>
    lateinit var uri :Uri
    // onCreate

    /**
     * 1st Approach to use the image from the galary
     * select with intent and start accitvity
     * 2nd approach to select the Image from the getcontent
     * you need to define a ActivityResultLauncher
     * next we need to set the URI and put the uri to it
     * and lunch the IMAge
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                this@imageUploadActivity.uri = it
               binding.ivuploadImage.setImageURI(it)
            }
        }
       // imageView = binding.ivuploadImage
        binding.btnchoose.setOnClickListener{
            // how to choose the image from galary and store it in imageview
//            val intent = Intent()
//            intent.action = Intent.ACTION_GET_CONTENT
//            intent.type = "image/*"
//            startActivityForResult(intent , 100)
            // Approcach 2
            getContent.launch("application/pdf")
        }

        // TODO upload the image in the Fire base
        binding.btnUpload.setOnClickListener{
            uploadImage()
        }

    }

    // Approcach 1 to Choose the Image - onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageView.setImageURI(imageUri)
        }
    }
    fun uploadImage(){
        if (uri != null){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.setMessage("Please wait while we upload the image...")
            progressDialog.show()

            // on below storage it will create the storage referance for fire base storage
            val ref: StorageReference = FirebaseStorage.getInstance().reference.child(UUID.randomUUID().toString())

            ref.putFile(uri!!).addOnSuccessListener {
               progressDialog.dismiss()
                Toast.makeText(this , "Image Uploaded" , Toast.LENGTH_SHORT).show()


            }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(this , "Image not Uploaded" , Toast.LENGTH_SHORT).show()
                }

        }


    }

}