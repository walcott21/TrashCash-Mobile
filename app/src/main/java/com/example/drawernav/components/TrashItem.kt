package com.example.drawernav.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drawernav.R
import com.example.drawernav.models.RewardHistory

import com.example.drawernav.models.TrashList

class TrashItemAdapter(private val dataList: List<TrashList>) : RecyclerView.Adapter<TrashItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trash_list_item, parent, false)
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
        fun bind(data: TrashList) {
            val nameTextView = itemView.findViewById<TextView>(R.id.trash_name)
            val dateTextView = itemView.findViewById<TextView>(R.id.trash_date)
            val weightTextView = itemView.findViewById<TextView>(R.id.trash_weight)

            nameTextView.text = data.name
            dateTextView.text = data.date
            weightTextView.text = data.weight.toString()
        }
    }
}
