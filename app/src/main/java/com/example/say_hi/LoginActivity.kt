package com.example.say_hi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.say_hi.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()

        binding.signup.setOnClickListener {
            val intent= Intent(this,SignUpActivity2::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            signIn()
        }

    }

    fun signIn(){
        val email=binding.email.text.toString()
        val pass1=binding.pass1.text.toString()
        if (email.isNotEmpty()&&pass1.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,pass1).addOnSuccessListener {
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                binding.email.text.clear()
                binding.pass1.text.clear()
            }.addOnFailureListener {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                binding.email.text.clear()
                binding.pass1.text.clear()
            }

        }
        else{
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }


    }
}