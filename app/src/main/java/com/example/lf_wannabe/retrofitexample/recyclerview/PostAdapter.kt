package com.example.lf_wannabe.retrofitexample.recyclerview

import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lf_wannabe.retrofitexample.R
import com.example.lf_wannabe.retrofitexample.model.Post

/**
 * Created by lf_wannabe on 05/09/2017.
 */
class PostAdapter(ac: FragmentActivity, hasHeader: Boolean, post:Post)
    : ListAdapterWithHeader<Post, PostHolder>(ac, hasHeader) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostHolder =
            PostHolder(LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.layout_post, parent, false))

    override fun onBindViewHolder(holder: PostHolder?, position: Int) {
        holder!!.onBind(getItem(position))
    }
}