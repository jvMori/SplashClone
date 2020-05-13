package com.jvmori.myapplication.common.data.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.jvmori.myapplication.R
import com.squareup.picasso.Picasso

@BindingAdapter("showImage")
fun loadImage(view: ImageView, url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.gradient)
        .into(view)
}

@BindingAdapter("loadImage")
fun bindImage(imageView: ImageView, url: String) {
    imageView.load(url){
        crossfade(true)
        placeholder(R.drawable.gradient)
        transformations(RoundedCornersTransformation(4f))
    }
}

@BindingAdapter("loadCircleImage")
fun bindCircleImage(imageView: ImageView, url: String) {
    imageView.load(url){
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}

