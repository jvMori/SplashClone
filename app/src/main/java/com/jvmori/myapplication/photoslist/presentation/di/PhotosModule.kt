package com.jvmori.myapplication.photoslist.presentation.di

import com.jvmori.myapplication.photoslist.data.local.LocalPhotosDataSourceImpl
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.local.PhotosDatabase
import com.jvmori.myapplication.photoslist.data.remote.Api
import com.jvmori.myapplication.photoslist.data.remote.RemotePhotosDataSourceImpl
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosList
import com.jvmori.myapplication.photoslist.data.repositories.PhotosRepositoryImpl
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import com.jvmori.myapplication.photoslist.domain.usecases.RefreshPhotos
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val photosModule = module {
    single {
        RefreshPhotos(get())
    }
    single {
        GetPhotosList(get())
    }
    factory {
        provideUnsplashApi(get())
    }
    single { (get() as PhotosDatabase).photosDao() }
    single<LocalPhotosDataSource<List<PhotoData>>> {
        LocalPhotosDataSourceImpl(
            get()
        )
    }
    single<RemotePhotosDataSource<List<PhotoDataResponse>>> {
        RemotePhotosDataSourceImpl(
            get()
        )
    }
    single<PhotosRepository> {
        PhotosRepositoryImpl(
            get(),
            get()
        )
    }
    viewModel {
        PhotosViewModel(
            get(),
            get()
        )
    }
}
fun provideUnsplashApi(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
}