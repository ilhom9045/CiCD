package com.magic.alladin.modules.records.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magic.alladin.R
import com.magic.alladin.modules.main.model.RecordsModel

class RecordRecyclerView(private val items: List<RecordsModel>) :
    RecyclerView.Adapter<RecordRecyclerView.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.nameTextView)
        val value: TextView = v.findViewById(R.id.valueTextView)

        fun bind(item: RecordsModel) {
            name.text = item.name
            value.text = item.value.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.record_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}