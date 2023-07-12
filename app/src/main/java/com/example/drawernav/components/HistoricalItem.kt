package com.example.drawernav.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drawernav.R
import com.example.drawernav.models.RewardHistory

class MyAdapter(private val dataList: List<RewardHistory>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.historical_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: RewardHistory) {
            // Atualize os elementos do item com os dados relevantes
            val nameTextView = itemView.findViewById<TextView>(R.id.name_historical_reward)
            val pointsTextView = itemView.findViewById<TextView>(R.id.date_historical_reward)
            nameTextView.text = data.name
            pointsTextView.text = data.date
        }
    }
}
