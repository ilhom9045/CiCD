package com.magic.alladin.modules.play.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magic.alladin.R
import com.magic.alladin.core.extention.openCloseCard
import com.magic.alladin.modules.play.callBack.UpdateItem
import com.magic.alladin.modules.play.model.CheckedFieldDataModel
import com.magic.alladin.modules.play.model.PlayModel

class PlayAdapter(
    private val items: List<PlayModel>,
    private val listener: Listener
) : RecyclerView.Adapter<PlayAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), UpdateItem {

        val imageView: ImageView = v.findViewById(R.id.imageView)

        fun bind(item: PlayModel) {
            update(item)
            item.listener = this
            itemView.setOnClickListener {
                if (item.isSuccess) {
                    return@setOnClickListener
                }
                if (!item.isClose) return@setOnClickListener
                item.isClose = !item.isClose
                imageView.openCloseCard(item) {
                    listener.onItemClick(item)
                }
            }
        }

        override fun update(item: PlayModel) {
            imageView.openCloseCard(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.play_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface Listener {

        fun onItemClick(item: PlayModel)

    }
}