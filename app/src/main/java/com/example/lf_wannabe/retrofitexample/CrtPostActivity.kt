package com.example.lf_wannabe.retrofitexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.lf_wannabe.retrofitexample.model.Post
import kotlinx.android.synthetic.main.activity_crt_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by lf_wannabe on 06/09/2017.
 */
class CrtPostActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crt_post)

        send_btn.setOnClickListener {
            // write api 호출할 때 id가 애매하네, 받을때는 필요한데
            writePost(Post(-1,"${title_input.text}", "${author_input.text}"))
            finish()
        }

    }

    fun writePost(post: Post){
        var call: Call<Post> = BaseApplication.customService.writePost(post)

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