package com.jvmori.myapplication.users.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.presentation.ui.BaseViewHolder
import com.jvmori.myapplication.databinding.UserItemBinding
import com.jvmori.myapplication.users.domain.UserEntity

class UsersViewHolder(private val binding: UserItemBinding) : BaseViewHolder<UserEntity, UsersViewHolder>(binding) {

    override fun bind(item: UserEntity) {
        binding.userEntity = item
    }

    companion object {
        fun create(parent: ViewGroup): UsersViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<UserItemBinding>(
                inflater,
                R.layout.user_item,
                parent,
                false
            )
            return UsersViewHolder(binding)
        }
    }
}