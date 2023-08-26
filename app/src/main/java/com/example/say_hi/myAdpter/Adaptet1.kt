package com.example.say_hi.myAdpter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.say_hi.ChatActivity
import com.example.say_hi.MainActivity
import com.example.say_hi.R
import com.example.say_hi.StoreDataType
import com.google.firebase.auth.FirebaseAuth

class Adaptet1(private val context: MainActivity, private val userList: MutableList<StoreDataType>):RecyclerView.Adapter<Adaptet1.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eachblock, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item =userList[position]
        holder.Name.text=item.Name


        Glide.with(holder.image)
            .load(item.downloadurl)
            .into(holder.image)

//        Picasso.get()
//            .load(item.downloadurl)
//            .into(holder.image)
        holder.itemView.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",item.Name)
            intent.putExtra("DownlaodUrl",item.downloadurl)
            intent.putExtra("UID",item.uid)
            context.startActivity(intent)
        }


    }
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
      val image=view.findViewById<ImageView>(R.id.imageView)
        val Name=view.findViewById<TextView>(R.id.name)
    }

}