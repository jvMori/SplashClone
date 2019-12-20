package com.jvmori.myapplication.features.photolist.presentation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer

import com.jvmori.myapplication.R
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PhotosFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by viewModel(PhotosViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<Photos>(
            inflater,
            R.layout.fragment_photos,
            container,
            false
        ).apply {
            viewModel = photosViewModel
            lifecycleOwner = this@PhotosFragment
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
        Log.i("PHOTOS", data?.toString())
    }
    private fun showError(errorMessage : String?){
        Log.i("PHOTOS", errorMessage ?: "")
    }

}
