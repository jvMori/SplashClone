package com.jvmori.myapplication.collectionslist.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.presentation.ui.BaseViewHolder
import com.jvmori.myapplication.databinding.CollectionItemBinding

class CollectionsViewHolder(private val binding: CollectionItemBinding) :
    BaseViewHolder<CollectionEntity, CollectionsViewHolder>(binding) {

    override fun bind(item: CollectionEntity) {
        binding.collectionItem = item
    }

    companion object{
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