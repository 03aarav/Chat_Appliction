package com.example.say_hi.MyViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.say_hi.StoreDataType
import com.example.say_hi.myAdpter.Adaptet1
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*

class ViewModel1: ViewModel() {



    var userList= MutableLiveData<List<StoreDataType>?>()
    var userList1 = mutableListOf<StoreDataType>()


    var  databaseReference= FirebaseDatabase.getInstance().getReference().child("User")

    fun getdatafromFirebase() {

        val firebasedata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val data = snapshot.value as? HashMap<String, HashMap<String, String>>
                Log.d("FirebaseData", "Data snapshot: $data")

                if (data != null) {
                    for ((_, value) in data) {
                        val name = value["name"] ?: ""
                        val phone = value["phone"] ?: ""
                        val userId = value["userid"] ?: ""
                        val downurl = value["downloadurl"] ?: ""
                        val uid =value["uid"]?:""
                        val user1 = StoreDataType(downurl, name, phone, userId,uid)
                        userList1.add(user1)

                    }
                    userList.postValue(userList1)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        databaseReference.addValueEventListener(firebasedata)

    }
}

