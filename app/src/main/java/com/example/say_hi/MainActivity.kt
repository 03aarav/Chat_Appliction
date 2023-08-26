package com.example.say_hi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.say_hi.MyViewModel.ViewModel1
import com.example.say_hi.databinding.ActivityMainBinding
import com.example.say_hi.myAdpter.Adaptet1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter1:Adaptet1
    private lateinit var databaseReference: DatabaseReference
    lateinit var viewModel1: ViewModel1
    var userList = mutableListOf<StoreDataType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference=FirebaseDatabase.getInstance().getReference().child("User")
        binding.Recylerview.layoutManager=LinearLayoutManager(this)
        viewModel1 = ViewModelProvider(this)[ViewModel1::class.java]
        binding.user.setOnClickListener {
                 val intent=Intent(this,RegisterActivity::class.java)
                 startActivity(intent)
                 }

           viewModel1.getdatafromFirebase()
           viewModel1.userList.observe(this){

               adapter1= Adaptet1(this, it as MutableList<StoreDataType>)
               binding.Recylerview.adapter=adapter1

           }

        databaseReference.child("User").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("name")
            .get().addOnSuccessListener {

            }



    }



//       private fun getdatafromFirebase(){
//           val firebasedata  = object :ValueEventListener {
//               override fun onDataChange(snapshot: DataSnapshot) {
//                   val data=snapshot.value as? HashMap<String, HashMap<String, String>>
//                   Log.d("FirebaseData", "Data snapshot: $data")
//
//                   if (data != null) {
//                       for ((_,value ) in data){
//                           val name= value["name"]?:""
//                           val phone=value["phone"]?:""
//                           val userId=value["userid"]?:""
//                           val downurl=value["downloadurl"]?:""
//                           val user1=StoreDataType(downurl,name,phone,userId)
//                           userList?.add(user1)
//                          // adapter1= userList?.let { Adaptet1(this, it) }!!
//                           binding.Recylerview.adapter=adapter1
//
//                       }
//                   }
//
//               }
//
//               override fun onCancelled(error: DatabaseError) {
//                   Toast.makeText(this@MainActivity, "failed to get the data", Toast.LENGTH_SHORT).show()
//               }
//
//           }
//           databaseReference.addValueEventListener(firebasedata)
//       }








}