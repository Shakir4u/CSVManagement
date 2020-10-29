package com.example.readingcsv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readingcsv.models.DataModel
import com.example.readingcsv.models.JsonModel
import com.google.gson.Gson

class DataAdapter(var context:Context, var listData: List<DataModel>):
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvData:TextView=itemView.findViewById(R.id.tvData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val holderView=LayoutInflater.from(context).inflate(R.layout.row_data,parent,false)
        return DataViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel=listData.get(position)
        holder.tvData.text=dataModel.toString()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun setData(list:List<DataModel>){
        listData=list
        notifyDataSetChanged()

    }
    fun getData():List<DataModel>{
        return listData
    }
}