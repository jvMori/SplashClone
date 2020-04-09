package com.jvmori.myapplication.collectionslist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
        viewmodel.run {
            setupRecyclerView(binding.collectionsRv)
            observeCollections()
        }
    }

    private fun CollectionsViewModel.observeCollections() {
        collections.observe(this@CollectionsFragment, Observer {
            collectionsAdapter.submitItems(it.data ?: listOf())
            if (it.status == Resource.Status.NETWORK_ERROR) {
                showNetworkInfo()
            }
        })
    }

    private fun showNetworkInfo() {
        Snackbar.make(
            this@CollectionsFragment.requireView(),
            getString(R.string.no_network_error),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun setupRecyclerView() {
        collectionsAdapter = CollectionsAdapter()
        binding.collectionsRv.run {
            adapter = collectionsAdapter
            layoutManager = LinearLayoutManager(this@CollectionsFragment.requireContext(), RecyclerView.VERTICAL, false)
        }
    }
}