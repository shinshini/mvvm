package com.example.primeropasoskotlin.ui.Post

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.primeropasoskotlin.R
import com.example.primeropasoskotlin.adapter.PostsAdapter
import com.example.primeropasoskotlin.models.Posts
import com.example.primeropasoskotlin.models.services.ApiClient
import com.example.primeropasoskotlin.models.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class listPosts : AppCompatActivity() {

    private lateinit var botonAdicionar: Button

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

        botonAdicionar = findViewById(R.id.btnAdicionar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Agrega algunos elementos de ejemplo
        postsList.add(Posts(1, 1, "Título 1", "Contenido 1"))
        postsList.add(Posts(1, 2, "Título 2", "Contenido 2"))

        adapter = PostsAdapter(postsList, this)
        recyclerView.adapter = adapter

        onAddClick()
    }

    // Función para editar el post al hacer clic en el botón "Editar"
    fun onEditClick(post: Posts) {
        val position = postsList.indexOfFirst { it.id == post.id }
        if (position == -1) return

        val dialogView = layoutInflater.inflate(R.layout.editar_texto, null).apply {
            findViewById<EditText>(R.id.editTextTitle).setText(post.title)
            findViewById<EditText>(R.id.editTextBody).setText(post.body)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar Post")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val body = dialogView.findViewById<EditText>(R.id.editTextBody).text.toString()
                if (title.isNotEmpty() && body.isNotEmpty()) {
                    postsList[position] = post.copy(title = title, body = body)
                    adapter.notifyItemChanged(position)
                } else {
                    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    fun onDeleteClick(post: Posts) {
        val position = postsList.indexOfFirst { it.id == post.id }
        if (position != -1) {
            AlertDialog.Builder(this)
                .setTitle("Eliminar Post")
                .setMessage("¿Estás seguro de que deseas eliminar este post?")
                .setPositiveButton("Eliminar") { _, _ ->
                    postsList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, postsList.size)
                }
                .setNegativeButton("Cancelar", null).show()
        }
    }

    fun onAddClick() {
        botonAdicionar.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.editar_texto, null)
            AlertDialog.Builder(this)
                .setTitle("Agregar Nuevo Post")
                .setView(view)
                .setPositiveButton("Agregar") { _, _ ->
                    val title = view.findViewById<EditText>(R.id.editTextTitle).text.toString()
                    val body = view.findViewById<EditText>(R.id.editTextBody).text.toString()
                    if (title.isNotEmpty() && body.isNotEmpty()) {
                        postsList.add(Posts(postsList.size + 1, postsList.size + 1, title, body))
                        adapter.notifyItemInserted(postsList.size - 1)
                    } else
                        Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null).show()
        }
    }
}


