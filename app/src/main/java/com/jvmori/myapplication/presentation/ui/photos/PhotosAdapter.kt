package com.jvmori.myapplication.presentation.ui.photos

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.domain.entities.PhotoEntity

class PhotosAdapter : PagedListAdapter<PhotoEntity, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var status: Resource.Status = Resource.Status.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE)
            PhotosViewHolder.create(parent)
        else
            ListFooterViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == DATA_VIEW_TYPE)
            (holder as PhotosViewHolder).bind(getItem(position))
        else
            (holder as ListFooterViewHolder).bind(status)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (status == Resource.Status.LOADING || status == Resource.Status.ERROR)
    }

    fun setState(status: Resource.Status) {
        this.status = status
        notifyItemChanged(super.getItemCount())
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