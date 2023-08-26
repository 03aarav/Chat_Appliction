package com.example.say_hi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.say_hi.databinding.ActivityRgisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRgisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storage: StorageReference
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRgisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.timg.setOnClickListener {
            activityLauncher.launch("image/*")
        }

        binding.tbtn.setOnClickListener {
            val name = binding.tname.text.toString()
            val phoneNo = binding.tphone.text.toString()
            val userId = binding.userid.text.toString()

            if (name.isNotEmpty() && phoneNo.isNotEmpty() && userId.isNotEmpty() && uri != null) {
                uploadImageAndSaveUser(name, phoneNo, userId)
            } else {
                Toast.makeText(this, "Please Enter the data and select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var activityLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        uri = it
        binding.timg.setImageURI(uri)
    }

    private fun uploadImageAndSaveUser(name: String, phoneNo: String, userId: String) {
         storage = FirebaseStorage.getInstance().reference.child("MyPhoto").child(auth.uid.toString())

        storage.putFile(uri!!).addOnSuccessListener { uploadTask ->
            uploadTask.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                val user = UserType(name, phoneNo, userId, imageUrl.toString(),FirebaseAuth.getInstance().currentUser?.uid.toString())
                 databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("User").child(auth.uid.toString())

                databaseReference.setValue(user).addOnSuccessListener {
                    Toast.makeText(this, "User Added successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)


    }
}