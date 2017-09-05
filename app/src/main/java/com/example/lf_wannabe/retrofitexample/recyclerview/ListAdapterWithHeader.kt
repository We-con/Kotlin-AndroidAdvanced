package com.example.lf_wannabe.retrofitexample.recyclerview

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by lf_wannabe on 05/09/2017.
 */
abstract class ListAdapterWithHeader<T , VH: RecyclerView.ViewHolder>(
        val activity: FragmentActivity, protected val hasHeader: Boolean)
    : RecyclerView.Adapter<VH>() {

    protected val HEADER_TYPE: Int = 0
    protected val ITEM_TYPE: Int = 1

    private lateinit var list: ArrayList<T>

    companion object {
        lateinit var mOnItemClickListener: OnItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int) {}
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasHeader && position == 0) HEADER_TYPE else ITEM_TYPE
    }

    override fun getItemCount(): Int {
        return list.size + if (hasHeader) 1 else 0
    }

    protected fun getItem(position: Int): T = list[position - if (hasHeader) 1 else 0]

    fun setData(list: ArrayList<T>) {
        this.list = list
        notifyDataSetChanged()
    }
}