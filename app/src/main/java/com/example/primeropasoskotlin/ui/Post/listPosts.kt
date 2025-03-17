package com.example.primeropasoskotlin.ui.Post

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.primeropasoskotlin.R
import com.example.primeropasoskotlin.adapter.PostsAdapter
import com.example.primeropasoskotlin.models.Posts

class listPosts : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostsAdapter
    private val postsList = mutableListOf<Posts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Agrega algunos elementos de ejemplo
        postsList.add(Posts(1, 1, "Título 1", "Contenido 1"))
        postsList.add(Posts(1, 2, "Título 2", "Contenido 2"))

        adapter = PostsAdapter(postsList, this)
        recyclerView.adapter = adapter
    }
    fun onEditClick(post: Posts) {
        // Manejar la acción de editar
    }

    fun onDeleteClick(post: Posts) {
        // Manejar la acción de eliminar
        postsList.remove(post)
        adapter.notifyDataSetChanged()
    }

}