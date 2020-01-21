package com.jvmori.myapplication.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jvmori.myapplication.R
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.presentation.ui.category.CategoryPageFragment

class CollectionsFragment : CategoryPageFragment() {

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
}