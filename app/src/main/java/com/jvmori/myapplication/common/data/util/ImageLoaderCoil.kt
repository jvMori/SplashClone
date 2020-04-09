package com.jvmori.myapplication.common.data.util

import android.widget.ImageView
import coil.api.load
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.domain.ImageLoader

class ImageLoaderCoil : ImageLoader {
    override fun load(imageView: ImageView, url: String) {
       imageView.load(url){
           placeholder(R.drawable.gradient)
       }
    }
}