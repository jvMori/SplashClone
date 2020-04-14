package com.jvmori.myapplication.collectionslist.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.databinding.CollectionItemBinding

class CollectionsViewHolder(private val binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(collectionItem: CollectionEntity) {
        binding.collectionItem = collectionItem
    }

    companion object {
        fun create(parent: ViewGroup): CollectionsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<CollectionItemBinding>(
                inflater,
                R.layout.collection_item,
                parent,
                false
            )
            return CollectionsViewHolder(binding)
        }
    }
}