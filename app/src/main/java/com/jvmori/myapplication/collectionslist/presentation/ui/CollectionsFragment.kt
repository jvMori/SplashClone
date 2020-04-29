package com.jvmori.myapplication.collectionslist.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.presentation.viewmodels.CollectionsViewModel
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.photoslist.data.remote.Order
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        viewmodel.run {
            observeCollections()
            observeNetworkState()
            collectionsAdapter.setRetryAction {
                this.retry()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.collections_menu, menu)
        (menu as MenuBuilder).setOptionalIconsVisible(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.all -> {
                viewmodel.changeCollectionTo(CollectionType.DefaultCollection)
                true
            }
            R.id.featured -> {
                viewmodel.changeCollectionTo(CollectionType.FeaturedCollection)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun CollectionsViewModel.observeCollections() {
        collections.observe(this@CollectionsFragment, Observer {
            collectionsAdapter.submitList(it)
        })
    }

    private fun CollectionsViewModel.observeNetworkState() {
        networkState.observe(this@CollectionsFragment, Observer {
            if (it != Resource.Status.SUCCESS) {
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