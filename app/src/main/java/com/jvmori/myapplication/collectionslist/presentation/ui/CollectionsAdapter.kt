package com.jvmori.myapplication.collectionslist.presentation.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity

class CollectionsAdapter : PagedListAdapter<CollectionEntity, RecyclerView.ViewHolder>(CollectionsDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CollectionsViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return currentList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CollectionsViewHolder).bind(getItem(position) ?: CollectionEntity(0))
    }

    companion object {
        val CollectionsDiffUtilCallback = object : DiffUtil.ItemCallback<CollectionEntity>() {
            override fun areItemsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}



