package com.jolly.androidx.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jolly.androidx.R
import com.jolly.androidx.Room.Word

class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private  var itemList:List<Word> = emptyList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
       return  ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.adapter_view,p0,false))
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.textView.text=itemList[p1].data
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val textView:TextView=itemView.findViewById(R.id.textView)

    }

    fun updateLsit(list:List<Word>){
        this.itemList=list
        notifyDataSetChanged()
    }

}
