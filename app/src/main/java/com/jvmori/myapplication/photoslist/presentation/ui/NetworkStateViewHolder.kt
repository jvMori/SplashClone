package com.jvmori.myapplication.photoslist.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.databinding.NetworkStateItemBinding

class NetworkStateViewHolder(binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private var networkState: Resource.Status? = null

    fun bind(networkState: Resource.Status?) {
        this.networkState = networkState
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<NetworkStateItemBinding>(
                inflater,
                R.layout.network_state_item,
                parent,
                false
            )
            return NetworkStateViewHolder(binding)
        }
    }
}
