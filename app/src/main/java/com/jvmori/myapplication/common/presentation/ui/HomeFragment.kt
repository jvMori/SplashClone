package com.jvmori.myapplication.common.presentation.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmori.myapplication.R
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.presentation.viewmodels.CollectionsViewModel
import com.jvmori.myapplication.common.presentation.ui.category.CategoryViewPagerAdapter
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by sharedViewModel<PhotosViewModel>()
    private val collectionsViewModel by inject<CollectionsViewModel>()
    private lateinit var binding: Photos
    private lateinit var navBarMenu: Menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<Photos>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ).apply {
            viewModel = photosViewModel
            lifecycleOwner = this@HomeFragment
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        navBarMenu = menu
        (menu as MenuBuilder).setOptionalIconsVisible(true)
        inflater.inflate(R.menu.photos_menu, menu)
        setPhotosMenuItemsVisible(true)
        setCollectionsMenuItemsVisible(false)
        onTabSelectedListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item) ||
                setupActionOnOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        setupViewPager()
    }

    private fun setupActionOnOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.latest -> {
                photosViewModel.changeOrder(Order.latest)
                true
            }
            R.id.popular -> {
                photosViewModel.changeOrder(Order.popular)
                true
            }
            R.id.oldest -> {
                photosViewModel.changeOrder(Order.oldest)
                true
            }
            R.id.all -> {
                collectionsViewModel.changeCollectionTo(CollectionType.DefaultCollection)
                true
            }
            R.id.featured -> {
                collectionsViewModel.changeCollectionTo(CollectionType.FeaturedCollection)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = CategoryViewPagerAdapter(this, 2)
        binding.photosViewPager.apply {
            adapter = pagerAdapter
            setPageTransformer(ZoomOutPageTransformer())
        }
        TabLayoutMediator(binding.tabs, binding.photosViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.photos)
                1 -> getString(R.string.collections)
                else -> ""
            }
        }.attach()
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).apply {
            if (this is MainActivity) {
                setSupportActionBar(binding.toolbar)
                setupActionBarWithNavController(navController, appBarConfiguration)
            }
        }
    }

    private fun onTabSelectedListener() {
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    getString(R.string.photos) -> {
                        setPhotosMenuItemsVisible(true)
                        setCollectionsMenuItemsVisible(false)
                    }
                    getString(R.string.collections) -> {
                        setPhotosMenuItemsVisible(false)
                        setCollectionsMenuItemsVisible(true)
                    }
                }
            }
        })
    }

    private fun setPhotosMenuItemsVisible(visible: Boolean) {
        navBarMenu.findItem(R.id.popular)?.isVisible = visible
        navBarMenu.findItem(R.id.latest)?.isVisible = visible
        navBarMenu.findItem(R.id.oldest)?.isVisible = visible
    }

    private fun setCollectionsMenuItemsVisible(visible: Boolean) {
        navBarMenu.findItem(R.id.all)?.isVisible = visible
        navBarMenu.findItem(R.id.featured)?.isVisible = visible
    }
}
