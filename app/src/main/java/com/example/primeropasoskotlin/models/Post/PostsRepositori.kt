package com.example.primeropasoskotlin.models.Post

import android.app.Application
import android.content.Context
import com.example.primeropasoskotlin.models.Posts
import com.example.primeropasoskotlin.models.services.ApiClient
import com.example.primeropasoskotlin.models.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsRepositori(context: Context) {

    private val apiService: ApiService = ApiClient.instance

     fun getAllPosts(callback: (List<Posts>?)-> Unit, errorCallback: (Throwable)-> Unit){

         apiService.getPosts().enqueue(object : Callback<List<Posts>>{
             override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                 if(response.isSuccessful){
                     callback(response.body())
                 }else{
                     errorCallback(Exception("Error: ${response.code()}"))
                 }
             }

             override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                errorCallback(t)
             }

         })

    }
    fun patchPost(id: Int, posts: Posts, callback: (Posts) -> Unit,errorCallback: (Throwable) -> Unit){
        apiService.patchPost(id,posts).enqueue(object :Callback<Posts>{
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if(response.isSuccessful){
                    callback(response.body()!!)
                    println("Data ${response.body()}")
                }else {
                    errorCallback(Throwable("error en la respuesta"))
                }
                }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                errorCallback(t)
            }

        })
    }
    fun getEli(id: Int ,  callback: (Unit) -> Unit,errorCallback: (Throwable) -> Unit){
        apiService.deletePost(id).enqueue(object :Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    callback(response.body()!!)
                    println("Data ${response.body()}")
                }else {
                    errorCallback(Throwable("error en la respuesta"))
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                errorCallback(t)
            }

        })
    }
}