package com.example.lf_wannabe.retrofitexample

import com.example.lf_wannabe.retrofitexample.model.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by lf_wannabe on 05/09/2017.
 */
interface CustomService {
    companion object {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://crud-mimmim.c9users.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @GET("/index")
    fun getPosts(): Call<List<Post>>

    @POST("/write")
    fun writePost(post: Post): Call<Post>
}