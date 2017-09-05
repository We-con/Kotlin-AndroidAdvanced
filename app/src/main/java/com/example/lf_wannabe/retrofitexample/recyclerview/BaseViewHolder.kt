package com.example.lf_wannabe.retrofitexample.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by lf_wannabe on 05/09/2017.
 */
abstract class BaseViewHolder<H, T>(itemView: View)
    : RecyclerView.ViewHolder(itemView){

    abstract fun onBind(item: T?)

    abstract fun onBindHeader(header: H?)

}
