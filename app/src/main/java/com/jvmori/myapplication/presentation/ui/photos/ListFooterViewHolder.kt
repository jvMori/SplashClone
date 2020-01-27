package com.jvmori.myapplication.presentation.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.databinding.FooterItemBinding

class ListFooterViewHolder (val binding : FooterItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(status : Resource.Status?){
        binding.status = status
    }

    companion object {
        fun create(parent: ViewGroup): ListFooterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<FooterItemBinding>(inflater, R.layout.footer_item, parent, false)
            return ListFooterViewHolder(binding)
        }
    }
}