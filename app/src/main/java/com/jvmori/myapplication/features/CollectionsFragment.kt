package com.jvmori.myapplication.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jvmori.myapplication.R
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.features.categories.presentation.CategoryPageFragment

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