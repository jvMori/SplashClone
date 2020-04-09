package com.jvmori.myapplication.photoslist.presentation.ui

import android.content.Context
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity

class PhotosAdapter(private val context : Context) : PagedListAdapter<PhotoEntity, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private var scrollingDown = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotosViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        handleItemAnimation(holder)
        (holder as PhotosViewHolder).bind(getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollingDown = dy <= 0
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    private fun handleItemAnimation(holder: RecyclerView.ViewHolder) {
        if (scrollingDown)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.item_slide_top)
        else
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.item_slide_bottom)
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