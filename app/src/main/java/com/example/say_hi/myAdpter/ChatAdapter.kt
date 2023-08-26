package com.example.say_hi.myAdpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.say_hi.ChatActivity
import com.example.say_hi.Message
import com.example.say_hi.R
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(private var context: ChatActivity, private var messageList: List<Message>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val item_sent=1
    private val item_reccieve=2

    class SentMessageViewHolder(view: View):RecyclerView.ViewHolder(view){
      val sentmessage=view.findViewById<TextView>(R.id.sent_message)

    }
    class RecieveMessageViewHolder(view: View):RecyclerView.ViewHolder(view){
       val  recieveMessage=view.findViewById<TextView>(R.id.recieve_message)
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         if (viewType==1){
             //inflate sent
             val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message_block, parent, false)
             return SentMessageViewHolder(view)
         }
         else {
             var view = LayoutInflater.from(parent.context)
                 .inflate(R.layout.recieved_message_block, parent, false)
             return RecieveMessageViewHolder(view)
         }
     }

     override fun getItemCount(): Int {
        return messageList.size
     }

     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

         val currentmessage=messageList[position]
         if (holder::class.java==SentMessageViewHolder::class.java){
             //do task related to sent message
             val viewHolder=holder as SentMessageViewHolder
             viewHolder.sentmessage.text=currentmessage.message
         }
         else
         {
             //Do task related to recieve message

             val viewHolder=holder as RecieveMessageViewHolder
             viewHolder.recieveMessage.text=currentmessage.message

         }
     }

    override fun getItemViewType(position: Int): Int {
        val currentmessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.toString()==currentmessage.UID){
            return item_sent
        }
        else
            return item_reccieve

    }
 }