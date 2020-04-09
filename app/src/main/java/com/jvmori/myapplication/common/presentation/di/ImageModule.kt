package com.jvmori.myapplication.common.presentation.di

import com.jvmori.myapplication.common.domain.ImageLoader
import com.jvmori.myapplication.common.data.util.ImageLoaderCoil
import org.koin.dsl.module

val imageModule = module {
    single<ImageLoader> { ImageLoaderCoil() }
}