package com.jvmori.myapplication.search.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.presentation.ui.CollectionsAdapter
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.domain.IOnClickListener
import com.jvmori.myapplication.common.presentation.ui.fragments.BaseFragment
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchCollectionsFragment : BaseFragment(R.layout.collections_fragment),
    SearchFragment.ISearchQuery,
    IOnClickListener {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val viewModel: SearchViewModel = searchScope.get()
    private lateinit var adapter: CollectionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =
            CollectionsAdapter.initAdapter((binding as CollectionsFragmentBinding).collectionsRv, this.requireContext())
                .apply {
                    onClickListener = this@SearchCollectionsFragment
                }
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
            (binding as CollectionsFragmentBinding).collectionsRv.recycledViewPool.clear()
            adapter.notifyDataSetChanged()
            adapter.submitList(it)
        })
    }

    override fun search(query: String?) {
        viewModel.setQuery(query)
    }

    override fun click(position: Int) {
        val id = adapter.getCollection(position)?.id ?: -1
        val action = SearchFragmentDirections.actionSearchFragmentToCollectionsPhotos(id)
        this.requireView().findNavController().navigate(action)
    }
}