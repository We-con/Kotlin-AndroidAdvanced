package com.example.lf_wannabe.retrofitexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.lf_wannabe.retrofitexample.model.Post
import com.example.lf_wannabe.retrofitexample.recyclerview.PostAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var customService: CustomService = CustomService.retrofit.create(CustomService::class.java)

    var postAdapter: PostAdapter = PostAdapter(this, false, Post())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vlist = ArrayList<Post>()

        postAdapter.setData(vlist)

        with(list){
            setHasFixedSize(false)
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        btn.setOnClickListener {
            getPosts()
        }

        create_post_btn.setOnClickListener {
            writePost()
//            startActivity(Intent(this, CrtPostActivity::class.java))
        }


    }

    fun getPosts() {
        var call: Call<List<Post>> = customService.getPosts()
        call.enqueue(object: Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                var list = response!!.body() as ArrayList<Post>
                postAdapter.setData(list)
                postAdapter.notifyDataSetChanged()
                Toast.makeText(applicationContext, list.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                Log.d("MIM", "호출 실패!")
            }
        })
    }

    fun writePost(){
        var call:Call<Post> = customService.writePost(Post())

        call.enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>?, response: Response<Post>?) {
                Log.d("MIM", "onResponse")
            }

            override fun onFailure(call: Call<Post>?, t: Throwable?) {
                Log.d("MIM", "onFailure")
            }
        })
    }
}
