package com.jvmori.myapplication.collectionslist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.presentation.viewmodels.CollectionsViewModel
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.domain.IOnClickListener
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.common.presentation.ui.fragments.HomeFragmentDirections
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import org.koin.android.ext.android.inject

class CollectionsFragment : CategoryPageFragment(), IOnClickListener {

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
            observeCollections()
            observeNetworkState()
            collectionsAdapter.setupRetryAction {
                this.retry()
            }
        }
    }

    override fun click(position: Int) {
        val id = collectionsAdapter.getCollection(position)?.id ?: -1
        val action = HomeFragmentDirections.actionHomeFragment2ToCollectionsPhotos(id)
        findNavController().navigate(action)
    }

    private fun CollectionsViewModel.observeCollections() {
        collections.observe(this@CollectionsFragment, Observer {
            collectionsAdapter.submitList(it)
        })
    }

    private fun CollectionsViewModel.observeNetworkState() {
        networkState.observe(this@CollectionsFragment, Observer {
            if (it != Resource.Status.SUCCESS) {
                collectionsAdapter.setupNetworkState(it)
            }
        })
    }

    private fun setupRecyclerView() {
        collectionsAdapter = CollectionsAdapter.initAdapter(binding.collectionsRv, this.requireContext()).apply {
            onClickListener = this@CollectionsFragment
        }
    }
}