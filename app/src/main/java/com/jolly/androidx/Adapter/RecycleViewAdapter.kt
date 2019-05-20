package com.jolly.androidx.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jolly.androidx.R
import com.jolly.androidx.Room.Word

class RecycleViewAdapter(context:Context) : androidx.recyclerview.widget.RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private lateinit var itemList:List<Word>

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
       return  ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.recycleview_layout,p0,false))
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.textView.text=itemList[p1].data
    }

    class ViewHolder(itemView:View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        val textView:TextView=itemView.findViewById(R.id.textView)

    }

    fun updateLsit(list:List<Word>){
        this.itemList=list
        notifyDataSetChanged()
    }

}
