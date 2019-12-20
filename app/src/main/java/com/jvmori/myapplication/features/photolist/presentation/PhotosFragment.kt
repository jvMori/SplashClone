package com.jvmori.myapplication.features.photolist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.databinding.CategoryPageBinding
import com.jvmori.myapplication.databinding.PhotosFragmentBinding
import com.jvmori.myapplication.features.categories.presentation.CategoryPageFragment
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import org.koin.android.viewmodel.ext.android.viewModel

class PhotosFragment : CategoryPageFragment() {

    private val photosViewModel: PhotosViewModel by viewModel(PhotosViewModel::class)
    private lateinit var binding : PhotosFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<PhotosFragmentBinding>(
            inflater,
            R.layout.photos_fragment,
            container,
            false
        ).apply {
            items = listOf()
        }
        return binding.root
    }

    private fun bindPhotos() {
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
        binding.items = data
        binding.textView.text = data?.get(0)?.url
    }
    private fun showError(errorMessage : String?){
        Log.i("PHOTOS", errorMessage ?: "")
    }
}