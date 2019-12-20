package com.jvmori.myapplication.features.photolist.presentation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.jvmori.myapplication.R
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.features.categories.presentation.CategoryViewModel
import com.jvmori.myapplication.features.categories.presentation.CategoryViewPagerAdapter
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by viewModel(PhotosViewModel::class)
    private val categoryViewModel : CategoryViewModel by viewModel(CategoryViewModel::class)
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
        photosViewModel.fetchPhotos()
        photosViewModel.photos.observe(this, Observer {
            when (it.status){
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> showSuccess(it.data)
                Resource.Status.ERROR -> showError(it.message)
            }
        })
    }
    private fun showLoading(){}
    private fun showSuccess(data : List<PhotoEntity>?){
        setupViewPager()
    }
    private fun setupViewPager(){
        val categories =  categoryViewModel.getCategories(listOf(), listOf())
        val pagerAdapter = CategoryViewPagerAdapter(this, categories)
        binding.photosViewPager.adapter = pagerAdapter
    }
    private fun showError(errorMessage : String?){
        Log.i("PHOTOS", errorMessage ?: "")
    }

}
