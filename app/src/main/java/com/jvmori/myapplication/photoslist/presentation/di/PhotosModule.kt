package com.jvmori.myapplication.photoslist.presentation.di

import com.jvmori.myapplication.common.data.PhotosDatabase
import com.jvmori.myapplication.photoslist.data.local.LocalPhotosDataSourceImpl
import com.jvmori.myapplication.photoslist.data.remote.Api
import com.jvmori.myapplication.photoslist.data.remote.RemotePhotosDataSourceImpl
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.data.repositories.PhotosRepositoryImpl
import com.jvmori.myapplication.photoslist.data.usecases.GetPhotosForCollectionImpl
import com.jvmori.myapplication.photoslist.data.usecases.GetPhotosListImpl
import com.jvmori.myapplication.photoslist.data.usecases.RefreshPhotosUseCaseImpl
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosForCollection
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.photoslist.domain.usecases.RefreshPhotosUseCase
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val photosModule = module {
    single<RefreshPhotosUseCase> { RefreshPhotosUseCaseImpl(get()) }
    single<GetPhotosListUseCase> { GetPhotosListImpl(get()) }
    factory { provideUnsplashApi(get()) }
    single<GetPhotosForCollection> { GetPhotosForCollectionImpl(get()) }
    single { (get() as PhotosDatabase).photosDao() }
    single<LocalPhotosDataSource> { LocalPhotosDataSourceImpl(get()) }
    single<RemotePhotosDataSource> { RemotePhotosDataSourceImpl(get()) }
    single<PhotosRepository> { PhotosRepositoryImpl(get(), get()) }
    single<PhotosDataSource> { (scope: CoroutineScope) -> PhotosDataSource(scope, get()) }
    single { (photoDataSource: PhotosDataSource) -> PhotosDataSourceFactory((photoDataSource)) }
    viewModel { PhotosViewModel() }
}

fun provideUnsplashApi(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
}