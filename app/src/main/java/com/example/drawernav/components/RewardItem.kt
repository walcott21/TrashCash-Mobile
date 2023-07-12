package com.example.drawernav.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drawernav.R
import com.example.drawernav.models.RewardsModel

class RewardItemAdpater(private val dataList: List<RewardsModel>) : RecyclerView.Adapter<RewardItemAdpater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reward_item, parent, false)
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
        fun bind(data: RewardsModel) {
            val nameTextView = itemView.findViewById<TextView>(R.id.name_reward)
            val pointsTextView = itemView.findViewById<TextView>(R.id.points_reward)
            nameTextView.text = data.name
            pointsTextView.text = data.points.toString()
        }
    }
}
