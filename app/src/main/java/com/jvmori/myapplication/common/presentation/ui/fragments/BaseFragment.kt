package com.jvmori.myapplication.common.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment(private val layout: Int) : Fragment() {

    protected lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            layout,
            container,
            false
        )
        return binding.root
    }
}