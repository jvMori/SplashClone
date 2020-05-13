package com.jvmori.myapplication.search.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.presentation.ui.fragments.BaseFragment
import com.jvmori.myapplication.databinding.CollectionsFragmentBinding
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import com.jvmori.myapplication.users.presentation.UsersAdapter
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchUsersFragment : BaseFragment(R.layout.collections_fragment), SearchFragment.ISearchQuery {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val viewModel: SearchViewModel = searchScope.get()
    private lateinit var adapter: UsersAdapter

    override fun search(query: String?) {
        viewModel.setQuery(query)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsersAdapter.initAdapter((binding as CollectionsFragmentBinding).collectionsRv, this.requireContext())
    }

    override fun onStart() {
        super.onStart()
        viewModel.apply {
            observeUsers(this)
            observeNetworkStatus(this)
            adapter.setupRetryAction {
                getUsersRetryAction()
            }
        }
    }

    private fun observeNetworkStatus(viewModel: SearchViewModel) {
        viewModel.usersNetworkStatus.observe(this, Observer {
            if (it != Resource.Status.SUCCESS) {
                adapter.setupNetworkState(it)
            }
        })
    }

    private fun observeUsers(viewModel: SearchViewModel) {
        viewModel.getUsers().observe(this, Observer {
            (binding as CollectionsFragmentBinding).collectionsRv.recycledViewPool.clear()
            adapter.notifyDataSetChanged()
            adapter.submitList(it)
        })
    }
}