package com.jvmori.myapplication.presentation.di

import com.jvmori.myapplication.data.local.PhotosDatabase
import com.jvmori.myapplication.domain.usecases.GetPhotosList
import com.jvmori.myapplication.data.repositories.PhotosRepositoryImpl
import com.jvmori.myapplication.domain.repositories.PhotosRepository
import com.jvmori.myapplication.domain.usecases.RefreshPhotos
import com.jvmori.myapplication.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val photosModule = module {
    single {
        RefreshPhotos(get())
    }
    single {
        GetPhotosList(get())
    }
    single { (get() as PhotosDatabase).photosDao() }
    single<PhotosRepository> {
        PhotosRepositoryImpl(
            get(),
            get()
        )
    }
    viewModel { PhotosViewModel(get(), get()) }
}