package com.jvmori.myapplication.presentation.ui.photos

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.domain.entities.PhotoEntity

class PhotosAdapter : PagedListAdapter<PhotoEntity, RecyclerView.ViewHolder>(NewsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotosViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotosViewHolder).bind(getItem(position))
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<PhotoEntity>() {
            override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}