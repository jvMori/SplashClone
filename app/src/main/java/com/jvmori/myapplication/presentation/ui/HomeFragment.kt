package com.jvmori.myapplication.presentation.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmori.myapplication.R
import com.jvmori.myapplication.data.remote.Order
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.presentation.ui.category.CategoryViewPagerAdapter
import com.jvmori.myapplication.presentation.ui.common.ZoomOutPageTransformer
import com.jvmori.myapplication.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.mockito.internal.matchers.Or

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by sharedViewModel<PhotosViewModel>()
    private lateinit var binding: Photos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        (menu as MenuBuilder).setOptionalIconsVisible(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.latest -> {
                photosViewModel.setOrder(Order.latest)
                true
            }
            R.id.popular -> {
                photosViewModel.setOrder(Order.popular)
                true
            }
            R.id.oldest -> {
                photosViewModel.setOrder(Order.oldest)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onStart() {
        super.onStart()
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter =
            CategoryViewPagerAdapter(this, 2)
        binding.photosViewPager.apply {
            adapter = pagerAdapter
            setPageTransformer(ZoomOutPageTransformer())
        }
        TabLayoutMediator(binding.tabs, binding.photosViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "PHOTOS"
                1 -> "COLLECTIONS"
                else -> ""
            }
        }.attach()
    }

}
