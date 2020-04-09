package com.jvmori.myapplication.common.domain

import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, url : String)
}