package com.example.primeropasoskotlin.ui.Post

import android.os.Bundle
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
        // Inflar el diseño del cuadro de diálogo
        val dialogView = layoutInflater.inflate(R.layout.editar_texto, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val bodyEditText = dialogView.findViewById<EditText>(R.id.editTextBody)

        // Establecer los valores actuales del post en los campos de texto
        titleEditText.setText(post.title)
        bodyEditText.setText(post.body)

        // Crear el cuadro de diálogo
        val builder = AlertDialog.Builder(this)
            .setTitle("Editar Post")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                // Obtener los valores editados
                val newTitle = titleEditText.text.toString()
                val newBody = bodyEditText.text.toString()

                // Validar los campos antes de actualizar
                if (newTitle.isNotBlank() && newBody.isNotBlank()) {
                    // Actualizar el objeto Post local
                    post.title = newTitle
                    post.body = newBody

                    // Llamar a la API para actualizar el post
                    ApiClient.instance.putPost(post.id, post)
                        .enqueue(object : Callback<Posts> {
                            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                                if (response.isSuccessful) {
                                    // Notificar al adaptador para actualizar la vista
                                    adapter.notifyDataSetChanged()

                                    // Mostrar un Toast para confirmar que el post fue actualizado
                                    Toast.makeText(this@listPosts, "Post actualizado", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Manejo de errores si la actualización falla
                                    Toast.makeText(this@listPosts, "Error al actualizar el post", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Posts>, t: Throwable) {
                                // Manejo de errores en caso de fallo de la solicitud
                                Toast.makeText(this@listPosts, "Error en la conexión", Toast.LENGTH_SHORT).show()
                            }
                        })
                } else {
                    // Validar si los campos están vacíos
                    Toast.makeText(this@listPosts, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }

                // Cerrar el diálogo
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                // Cerrar el diálogo sin hacer nada
                dialog.dismiss()
            }

        // Mostrar el cuadro de diálogo
        builder.show()
    }


    fun onDeleteClick(post: Posts) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Eliminar Post")
            .setMessage("¿Estás seguro de que deseas eliminar este post?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                // Eliminar el post de la lista
                postsList.remove(post)

                // Notificar al adaptador para actualizar la vista
                adapter.notifyDataSetChanged()

                // Mostrar un Toast para confirmar que el post fue eliminado
                Toast.makeText(this, "Post eliminado", Toast.LENGTH_SHORT).show()

                // Cerrar el diálogo
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                // Cerrar el diálogo sin hacer nada
                dialog.dismiss()
            }

        // Mostrar el cuadro de diálogo de confirmación
        builder.show()
    }


}