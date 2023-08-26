package com.example.say_hi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.say_hi.databinding.ActivitySignUp2Binding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySignUp2Binding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        binding.signup.setOnClickListener {
            signUp()
        }
        binding.bckbtn.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }
    fun signUp(){
        val email=binding.emailSignUp.text.toString()
        val pass1=binding.passwordSignup.text.toString()
        val pass2 =binding.passwordSignup2.text.toString()

        if (email.isNotEmpty()&&pass1.isNotEmpty()&&pass1==pass2){
           auth.createUserWithEmailAndPassword(email,pass1).addOnSuccessListener {
               Toast.makeText(this, "Please Login Here", Toast.LENGTH_SHORT).show()
               val intent=Intent(this,LoginActivity::class.java)
               startActivity(intent)
           }
               .addOnFailureListener {
                   Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
               }
        }
        else{
            Toast.makeText(this, "Please Enter the email and password ", Toast.LENGTH_SHORT).show()
        }
    }
}