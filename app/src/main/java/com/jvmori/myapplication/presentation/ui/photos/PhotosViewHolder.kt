package com.jvmori.myapplication.presentation.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.databinding.PhotoItemBinding
import com.jvmori.myapplication.domain.entities.PhotoEntity

class PhotosViewHolder(private var binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: PhotoEntity?){
        binding.photoData = photo
    }

    companion object {
        fun create(parent: ViewGroup): PhotosViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<PhotoItemBinding>(inflater, R.layout.photo_item, parent, false)
            return PhotosViewHolder(binding)
        }
    }
}