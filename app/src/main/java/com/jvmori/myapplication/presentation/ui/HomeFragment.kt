package com.jvmori.myapplication.presentation.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmori.myapplication.R
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.presentation.ui.category.CategoryViewPagerAdapter
import com.jvmori.myapplication.presentation.ui.common.ZoomOutPageTransformer
import com.jvmori.myapplication.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by viewModel(
        PhotosViewModel::class
    )
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
            tab.text =  when (position){
                0 -> "PHOTOS"
                1 -> "COLLECTIONS"
                else -> ""
            }
        }.attach()
    }

}
