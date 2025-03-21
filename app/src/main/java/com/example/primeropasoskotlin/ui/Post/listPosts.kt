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

    private lateinit var add:Button

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

        add=findViewById(R.id.button2)
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
        if (position != -1) {
            AlertDialog.Builder(this).apply {
                setTitle("Editar Post")
                val dialogView = layoutInflater.inflate(R.layout.editar_texto, null)
                val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
                val bodyEditText = dialogView.findViewById<EditText>(R.id.editTextBody)

                titleEditText.setText(post.title)
                bodyEditText.setText(post.body)

                setView(dialogView)
                setPositiveButton("Guardar") { _, _ ->
                    val newTitle = titleEditText.text.toString().trim()
                    val newBody = bodyEditText.text.toString().trim()

                    if (newTitle.isNotEmpty() && newBody.isNotEmpty()) {
                        postsList[position] = post.copy(title = newTitle, body = newBody)
                        adapter.notifyItemChanged(position) // Actualiza solo el ítem modificado
                    } else {
                        Toast.makeText(
                            this@listPosts,
                            "Por favor, complete todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                show()
            }
        }
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
                    adapter.notifyItemRangeChanged(position, postsList.size) // Ajusta posiciones en RecyclerView
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        }
    fun onAddClick() {
        add.setOnClickListener {
        // Inflar el diseño del cuadro de diálogo para agregar un nuevo post
        val dialogView = layoutInflater.inflate(R.layout.editar_texto, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val bodyEditText = dialogView.findViewById<EditText>(R.id.editTextBody)

        AlertDialog.Builder(this)
            .setTitle("Agregar Nuevo Post")
            .setView(dialogView)
            .setPositiveButton("Agregar") { _, _ ->
                val newTitle = titleEditText.text.toString().trim()
                val newBody = bodyEditText.text.toString().trim()

                if (newTitle.isNotEmpty() && newBody.isNotEmpty()) {
                    // Crear un nuevo post
                    val newPost = Posts(postsList.size + 1, postsList.size + 1, newTitle, newBody)

                    // Agregar el nuevo post a la lista
                    postsList.add(newPost)

                    // Notificar al adaptador para actualizar el RecyclerView
                    adapter.notifyItemInserted(postsList.size - 1)
                } else {
                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
       }
    }


