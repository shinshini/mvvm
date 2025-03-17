package com.example.primeropasoskotlin.models.Post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.primeropasoskotlin.models.Posts

class PostsDao(private val postRepositori: PostsRepositori) {

    private val _posts = MutableLiveData<List<Posts>>()
    val posts: LiveData<List<Posts>> get() =  _posts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

   private val _editar = MutableLiveData<Boolean>()
    val editar: LiveData<Boolean> get()= _editar

    private val _elim = MutableLiveData<Boolean>()
    val elim : LiveData<Boolean> get()= _elim


    fun getPosts(){
        postRepositori.getAllPosts(
             callback = {postList -> _posts.value = postList},
            errorCallback ={thorwable -> _error.value = thorwable.message}
        )

    }
    fun geteditas(id: Int,posts: Posts){
        postRepositori.patchPost(
           id,
            posts = posts,
            callback = { _editar.value=true },
            errorCallback = {_editar.value=false }
        )
    }

    fun getElimi(id: Int){
        postRepositori.getEli(
            id ,
            callback = { _elim.value=true },
            errorCallback = {_elim.value=false }
        )
    }

}