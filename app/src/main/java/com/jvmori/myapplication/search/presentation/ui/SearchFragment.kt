package com.jvmori.myapplication.search.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.presentation.ui.MainActivity
import com.jvmori.myapplication.databinding.SearchFragmentBinding
import com.jvmori.myapplication.search.data.Orientation

class SearchFragment : Fragment(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var searchView: SearchView
    private lateinit var viewPagerAdapter: SearchViewPagerAdapter
    var spinnerItemSelected: ISpinnerItemSelected? = null

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
        setupToolbar()
        setupViewPager()
        setupPhotoOrientationsSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        setupSearchViewInAppBar(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewPagerAdapter.iSearchQuery?.search(query)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerItemSelected?.select(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            if (this is MainActivity) {
                setSupportActionBar(binding.toolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
                setupActionBarWithNavController(navController, appBarConfiguration)
            }
        }
    }

    private fun setupViewPager() {
        viewPagerAdapter = SearchViewPagerAdapter(this).apply {
            binding.viewPager.adapter = this
            addOnPageChangedListener()
        }
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.photos)
                1 -> getString(R.string.collections)
                2 -> getString(R.string.users)
                else -> ""
            }
        }.attach()
    }

    private fun addOnPageChangedListener() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                setISearchQueryListener(position)
                setOptionsToolbarVisibility(position)
            }

            private fun setOptionsToolbarVisibility(position: Int) {
                binding.optionsToolbar.visibility = when (position) {
                    0 -> View.VISIBLE
                    else -> View.GONE
                }
            }

            private fun setISearchQueryListener(position: Int) {
                viewPagerAdapter.iSearchQuery = when (position) {
                    0 -> viewPagerAdapter.searchPhotosFragment
                    1 -> viewPagerAdapter.searchCollectionsFragment
                    2 -> viewPagerAdapter.searchUsersFragment
                    else -> null
                }
            }
        })
    }

    private fun setupPhotoOrientationsSpinner() {
        val orientations = Orientation.values().map { it.toString() }
        binding.spinner.onItemSelectedListener = this
        binding.spinner.adapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            orientations
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
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

    interface ISearchQuery {
        fun search(query: String?)
    }

    interface ISpinnerItemSelected {
        fun select(position: Int)
    }
}