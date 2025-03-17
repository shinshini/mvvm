package com.example.primeropasoskotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.primeropasoskotlin.models.Post.PostsDao
import com.example.primeropasoskotlin.models.Post.PostsRepositori
import com.example.primeropasoskotlin.models.Posts

class PostViewModel(application:Application):AndroidViewModel(application) {

    private val postsDao:PostsDao

    val post:LiveData<List<Posts>> get() = postsDao.posts
    val error:LiveData<String> get() = postsDao.error

    val edit : LiveData<Boolean>get()=postsDao.editar

    val eliminar : LiveData<Boolean>get()=postsDao.elim

    init {
        val postsRepositori = PostsRepositori(application)
        postsDao = PostsDao(postsRepositori)
    }

    fun getPost(){
        postsDao.getPosts()
    }

    fun getEditar(id:Int,posts: Posts){
        postsDao.geteditas(id, posts )
    }

    fun getEliminar(id:Int){
        postsDao.getElimi(id )
    }
}