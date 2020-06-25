package com.jvmori.myapplication.users.presentation

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.photoslist.presentation.ui.NetworkStateViewHolder
import com.jvmori.myapplication.users.domain.UserEntity

//class UsersAdapter : BaseAdapter<UserEntity>(R.layout.user_item, userDiffUtil) {
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (getItemViewType(position)) {
//            R.layout.user_item -> (holder as UsersViewHolder).bind(getItem(position) ?: UserEntity())
//            R.layout.network_state_item -> (holder as NetworkStateViewHolder).bind(networkState, retryAction)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            R.layout.user_item -> UsersViewHolder.create(parent)
//            R.layout.network_state_item -> NetworkStateViewHolder.create(parent)
//            else -> throw IllegalArgumentException("unknown view type $viewType")
//        }
//    }
//
//    companion object {
//        val userDiffUtil = object : DiffUtil.ItemCallback<UserEntity>() {
//            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
//                return oldItem.userId == newItem.userId
//            }
//        }
//
//        fun initAdapter(recyclerView: RecyclerView, context: Context): UsersAdapter {
//            return UsersAdapter().apply {
//                recyclerView.adapter = this
//                recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//            }
//        }
//    }
//}