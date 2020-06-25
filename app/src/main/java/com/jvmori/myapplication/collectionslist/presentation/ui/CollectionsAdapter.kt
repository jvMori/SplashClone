package com.jvmori.myapplication.collectionslist.presentation.ui

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.domain.IOnClickListener

class CollectionsAdapter : PagingDataAdapter<CollectionEntity, CollectionsViewHolder>(CollectionsDiffUtilCallback) {

    var onClickListener: IOnClickListener? = null

    fun getCollection(position: Int): CollectionEntity? {
        return getItem(position)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(getItem(position) ?: CollectionEntity(0))
        holder.itemView.setOnClickListener {
            onClickListener?.click(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder =
        CollectionsViewHolder.create(parent)

    companion object {
        val CollectionsDiffUtilCallback = object : DiffUtil.ItemCallback<CollectionEntity>() {
            override fun areItemsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity): Boolean {
                return oldItem == newItem
            }
        }

        fun initAdapter(recyclerView: RecyclerView, context: Context): CollectionsAdapter {
            return CollectionsAdapter().apply {
                recyclerView.adapter = this
                recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
    }
}



