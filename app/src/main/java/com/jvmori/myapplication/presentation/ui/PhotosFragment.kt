package com.jvmori.myapplication.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR.photoData
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.databinding.PhotosFragmentBinding
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PhotosFragment : CategoryPageFragment() {

    private val photosViewModel: PhotosViewModel by viewModel(
        PhotosViewModel::class
    )
    private lateinit var binding: PhotosFragmentBinding

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
            //items = listOf()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bindPhotos()
    }

    private fun bindPhotos() {
        photosViewModel.fetchPhotos()
        photosViewModel.photos.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> showSuccess(it.data)
                Resource.Status.ERROR -> showError(it.message)
            }
        })
    }

    private fun showLoading() {}
    private fun showSuccess(data: List<PhotoEntity>?) {
        binding.setVariable(photoData, data?.get(0) ?: PhotoEntity("", 1))
    }

    private fun showError(errorMessage: String?) {
        Log.i("PHOTOS", errorMessage ?: "")
    }
}