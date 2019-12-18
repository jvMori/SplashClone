package com.jvmori.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.features.photolist.data.models.PhotoData
import com.jvmori.myapplication.features.photolist.presentation.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val photosViewModel : PhotosViewModel by viewModel(PhotosViewModel::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    private fun showSuccess(data : List<PhotoData>?){
        Log.i("PHOTOS", data?.toString())
    }
    private fun showError(errorMessage : String?){
        Log.i("PHOTOS", errorMessage ?: "")
    }
}
