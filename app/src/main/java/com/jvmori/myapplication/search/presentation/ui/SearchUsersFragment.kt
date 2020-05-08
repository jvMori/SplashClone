package com.jvmori.myapplication.search.presentation.ui

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchUsersFragment : Fragment(R.layout.fragment_search_photos), SearchFragment.ISearchQuery {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val viewModel: SearchViewModel = searchScope.get()

    override fun search(query: String?) {
        viewModel.setQuery(query)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUsers().observe(this, Observer {
            Log.i("PHOTOS", it.toString())
        })
    }
}