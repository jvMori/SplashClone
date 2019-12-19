package com.jvmori.myapplication.di

import com.jvmori.myapplication.core.data.db.PhotosDatabase
import com.jvmori.myapplication.features.photolist.data.datasources.remote.PhotosNetworkDataSource
import com.jvmori.myapplication.features.photolist.data.datasources.remote.PhotosNetworkDataSourceImpl
import com.jvmori.myapplication.features.photolist.data.repositories.PhotosRepositoryImpl
import com.jvmori.myapplication.features.photolist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.features.photolist.presentation.PhotosViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val photosModule = module {
    single<PhotosNetworkDataSource> {
        PhotosNetworkDataSourceImpl(
            get()
        )
    }
    single { (get() as PhotosDatabase).photosDao() }
    single<PhotosRepository> { PhotosRepositoryImpl(get(), get()) }
    viewModel { PhotosViewModel(get()) }
}