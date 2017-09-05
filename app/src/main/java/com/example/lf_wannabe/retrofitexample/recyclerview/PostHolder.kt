package com.example.lf_wannabe.retrofitexample.recyclerview

import android.view.View
import android.widget.Toast
import com.example.lf_wannabe.retrofitexample.model.Post
import kotlinx.android.synthetic.main.layout_post.view.*

/**
 * Created by lf_wannabe on 05/09/2017.
 */
class PostHolder(itemView: View) : BaseViewHolder<Post, Post>(itemView) {
    override fun onBind(item: Post?) {
        var content = ""
        with(item!!){
            content = "제목 : ${title} \n저자 : ${author} \n생성일시 : ${created_at}"
        }
        itemView.post_id.text = content
    }

    override fun onBindHeader(header: Post?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}