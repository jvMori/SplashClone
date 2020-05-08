package com.jvmori.myapplication.collectionslist.presentation.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.presentation.ui.BaseAdapter
import com.jvmori.myapplication.photoslist.presentation.ui.NetworkStateViewHolder

class CollectionsAdapter : BaseAdapter<CollectionEntity>( R.layout.collection_item, CollectionsDiffUtilCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.collection_item -> (holder as CollectionsViewHolder).bind(getItem(position) ?: CollectionEntity(0))
            R.layout.network_state_item -> (holder as NetworkStateViewHolder).bind(networkState, retryAction)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.collection_item -> CollectionsViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
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

        fun initAdapter(recyclerView: RecyclerView, context: Context): CollectionsAdapter {
            return CollectionsAdapter().apply {
                recyclerView.adapter = this
                recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
    }
}



