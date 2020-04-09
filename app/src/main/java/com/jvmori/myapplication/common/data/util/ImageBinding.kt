package com.jvmori.myapplication.common.data.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("loadImage")
fun bindImage(imageView: ImageView, url: String) {
    imageView.load(url)
}



