package com.jvmori.myapplication.presentation.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jvmori.myapplication.R
import com.squareup.picasso.Picasso


@BindingAdapter("showImage")
fun loadImage(view: ImageView, url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.gradient)
        .into(view)
}
