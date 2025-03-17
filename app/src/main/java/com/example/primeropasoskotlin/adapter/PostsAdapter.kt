package com.example.primeropasoskotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.primeropasoskotlin.R
import com.example.primeropasoskotlin.models.Posts
import com.example.primeropasoskotlin.ui.Post.listPosts

class PostsAdapter(private val postList: List<Posts>, private val listener: listPosts): RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    interface  OnItemClickListener{
        fun onEditClick (posts: Posts)
        fun omDeleteClick (posts: Posts)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.component_list_post, parent, false)
       return  ViewHolder(view)
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.textTitle)
        val body: TextView = view.findViewById(R.id.textBody)
        val editButton: Button = view.findViewById(R.id.buttonEdit)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)

    }

    override fun onBindViewHolder(holder: PostsAdapter.ViewHolder, position: Int) {
        val post = postList[position]
        holder.title.text = post.title
        holder.body.text = post.body

        holder.editButton.setOnClickListener { listener.onEditClick(post) }
        holder.deleteButton.setOnClickListener { listener.onDeleteClick(post) }

    }

    override fun getItemCount(): Int = postList.size


}