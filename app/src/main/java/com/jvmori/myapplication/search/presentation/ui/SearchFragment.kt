package com.jvmori.myapplication.search.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.presentation.ui.MainActivity
import com.jvmori.myapplication.databinding.SearchFragmentBinding

class SearchFragment : Fragment(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener{

    private lateinit var binding: SearchFragmentBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<SearchFragmentBinding>(
            inflater,
            R.layout.search_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).apply {
            if (this is MainActivity) {
                setSupportActionBar(binding.toolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
                setupActionBarWithNavController(navController, appBarConfiguration)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        setupSearchViewInAppBar(menu)
    }

    private fun setupSearchViewInAppBar(menu: Menu) {
        searchView = (menu.findItem(R.id.action_search)?.actionView as SearchView).apply {
            queryHint = resources.getString(R.string.search_items)
            isActivated = true
            onActionViewExpanded()
            isIconified = false
            setOnQueryTextListener(this@SearchFragment)
            setOnCloseListener(this@SearchFragment)
        }
    }

        override fun onQueryTextSubmit(query: String?): Boolean {
            searchView.apply {
                isIconified = true
                onActionViewCollapsed()
                clearFocus()
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

        override fun onClose(): Boolean {
            searchView.apply {
                onActionViewCollapsed()
                clearFocus()
            }
            return true
        }
    }