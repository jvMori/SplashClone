package com.jvmori.myapplication.presentation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jvmori.myapplication.R
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.presentation.ui.category.CategoryViewPagerAdapter
import com.jvmori.myapplication.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by viewModel(
        PhotosViewModel::class)
    private lateinit var binding : Photos

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

    override fun onStart() {
        super.onStart()
        setupViewPager()
    }

    private fun setupViewPager(){
        val pagerAdapter =
            CategoryViewPagerAdapter(this, 2)
        binding.photosViewPager.adapter = pagerAdapter
    }

}
