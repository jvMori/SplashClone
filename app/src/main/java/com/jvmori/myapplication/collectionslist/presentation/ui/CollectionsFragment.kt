package com.jvmori.myapplication.collectionslist.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.presentation.viewmodels.CollectionsViewModel
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import org.koin.android.ext.android.inject

class CollectionsFragment : CategoryPageFragment() {

    private val viewmodel by inject<CollectionsViewModel>()
    private lateinit var collectionsAdapter: CollectionsAdapter
    private lateinit var binding: CollectionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<CollectionsFragmentBinding>(
            inflater,
            R.layout.collections_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        collectionsAdapter.setRetryAction {
            Log.i("", "Click")
        }
        viewmodel.run {
            observeCollections()
            observeNetworkState()
        }
    }

    private fun CollectionsViewModel.observeCollections() {
        collections.observe(this@CollectionsFragment, Observer {
            collectionsAdapter.submitList(it)
        })
    }

    private fun CollectionsViewModel.observeNetworkState() {
        networkState.observe(this@CollectionsFragment, Observer {
            if (it == Resource.Status.ERROR || it == Resource.Status.NETWORK_ERROR) {
                collectionsAdapter.setNetworkState(it)
            }
        })
    }

    private fun setupRecyclerView() {
        collectionsAdapter = CollectionsAdapter()
        binding.collectionsRv.run {
            adapter = collectionsAdapter
            layoutManager = LinearLayoutManager(this@CollectionsFragment.requireContext(), RecyclerView.VERTICAL, false)
        }
    }
}