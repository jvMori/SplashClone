package com.jvmori.myapplication.common.presentation.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.data.remote.Resource

open class BaseAdapter<T>(
    private val layoutItem: Int,
    private val diffUtil: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffUtil) {

    protected var networkState: Resource.Status? = null
    protected lateinit var retryAction: () -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        throw IllegalArgumentException("unknown view type $viewType")
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            layoutItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    fun setupNetworkState(newNetworkState: Resource.Status?) {
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

    fun setupRetryAction(retryAction: () -> Unit) {
        this.retryAction = retryAction
    }

    private fun hasExtraRow() = networkState != null && isNotSuccess()

    private fun isNotSuccess() = networkState != Resource.Status.SUCCESS

}



