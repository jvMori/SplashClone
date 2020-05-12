package com.jvmori.myapplication.search.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.presentation.ui.CollectionsAdapter
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchCollectionsFragment : Fragment(),
    SearchFragment.ISearchQuery {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val viewModel: SearchViewModel  = searchScope.get()
    private lateinit var binding: CollectionsFragmentBinding
    private lateinit var adapter : CollectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.collections_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CollectionsAdapter.initAdapter(binding.collectionsRv, this.requireContext())
    }

    override fun onStart() {
        super.onStart()
        viewModel.apply {
            observeCollections()
            observeNetworkStatus()
            adapter.setupRetryAction {
                getCollectionsRetryAction()
            }
        }
    }

    private fun SearchViewModel.observeNetworkStatus() {
        collectionsNetworkStatus.observe(this@SearchCollectionsFragment, Observer {
            if (it != Resource.Status.SUCCESS) {
                adapter.setupNetworkState(it)
            }
        })
    }

    private fun SearchViewModel.observeCollections() {
        getCollections().observe(this@SearchCollectionsFragment, Observer {
            binding.collectionsRv.recycledViewPool.clear()
            adapter.notifyDataSetChanged()
            adapter.submitList(it)
        })
    }

    override fun search(query: String?) {
        viewModel.setQuery(query)
    }

}