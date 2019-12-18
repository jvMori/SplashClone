package com.jvmori.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.jvmori.myapplication.features.photolist.presentation.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val photosViewModel : PhotosViewModel by viewModel(PhotosViewModel::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photosViewModel.fetchPhotos()
        photosViewModel.photos.observe(this, Observer {
            Log.i("PHOTOS", it[0].urls.small)
        })
    }
}
