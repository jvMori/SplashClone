package com.jvmori.myapplication.collectionslist.presentation.ui

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.presentation.ui.NetworkStateViewHolder

class CollectionsAdapter : PagedListAdapter<CollectionEntity, RecyclerView.ViewHolder>(CollectionsDiffUtilCallback) {

    private var networkState : Resource.Status? = null
    private lateinit var retryAction : () -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.collection_item -> CollectionsViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.collection_item -> (holder as CollectionsViewHolder).bind(getItem(position) ?: CollectionEntity(0))
            R.layout.network_state_item -> (holder as NetworkStateViewHolder).bind(networkState, retryAction)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.collection_item
        }
    }

    fun setNetworkState(newNetworkState: Resource.Status?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    fun setRetryAction(retryAction : () -> Unit){
        this.retryAction = retryAction
    }

    private fun hasExtraRow() = networkState != null && isNotSuccess()

    private fun isNotSuccess() = networkState != Resource.Status.SUCCESS

    companion object {
        val CollectionsDiffUtilCallback = object : DiffUtil.ItemCallback<CollectionEntity>() {
            override fun areItemsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity): Boolean {
                return oldItem == newItem
            }
        }

        fun initAdapter(recyclerView: RecyclerView, context : Context) : CollectionsAdapter{
            return CollectionsAdapter().apply {
                recyclerView.adapter = this
                recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
    }
}



