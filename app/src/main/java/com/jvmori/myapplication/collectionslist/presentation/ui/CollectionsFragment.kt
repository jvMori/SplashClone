package com.jvmori.myapplication.collectionslist.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.presentation.viewmodels.CollectionsViewModel
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import org.koin.android.ext.android.inject

class CollectionsFragment : CategoryPageFragment() {

    private val viewmodel by inject<CollectionsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<CollectionsFragmentBinding>(
            inflater,
            R.layout.collections_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewmodel.collections.observe(this, Observer {
            Log.i("collections", it.toString())
        })
    }
}