package com.example.say_hi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.say_hi.databinding.ActivityChatBinding
import com.example.say_hi.myAdpter.ChatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var chatAdapter: ChatAdapter
    lateinit var messagelist:MutableList<Message>
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

           messagelist= mutableListOf()
            databaseReference=FirebaseDatabase.getInstance().getReference()

        val name =intent.getStringExtra("name")
        val ReciverUID=intent.getStringExtra("UID")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid.toString()

           binding.recyclerView.layoutManager=LinearLayoutManager(this)
           chatAdapter= ChatAdapter(this,messagelist)

        binding.recyclerView.adapter=chatAdapter

        val senderRoom=ReciverUID+senderUid
        val reiciverRoom=senderUid+ReciverUID
//
//        binding.sendingMessage.text=uid

        //logic for showing it to recyer view

        databaseReference.child("Chat").child(senderRoom).child("message")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for (snap in snapshot.children) {
                        val message = snap.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    chatAdapter.notifyDataSetChanged()
                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )

        supportActionBar?.title=name

        binding.sentbtn.setOnClickListener {
            val message1=binding.sendingMessage.text.toString()
            val messageObj=Message(message1,senderUid)

            databaseReference.child("Chat").child(senderRoom).child("message").push()
                .setValue(messageObj).addOnSuccessListener {
                    databaseReference.child("Chat").child(reiciverRoom).child("message").push()
                        .setValue(messageObj)
                }
            binding.sendingMessage.text.clear()
        }
    }
}