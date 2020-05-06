package com.jvmori.myapplication.search.presentation.di

import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.search.data.remote.SearchApi
import com.jvmori.myapplication.search.data.remote.SearchRemoteDataSourceImpl
import com.jvmori.myapplication.search.data.repositories.CollectionsDataSource
import com.jvmori.myapplication.search.data.repositories.SearchRepositoryImpl
import com.jvmori.myapplication.search.data.usecases.SearchCollectionsUseCaseImpl
import com.jvmori.myapplication.search.data.usecases.SearchPhotosUseCaseImpl
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource
import com.jvmori.myapplication.search.domain.repositories.SearchRepository
import com.jvmori.myapplication.search.domain.usecases.SearchCollectionsUseCase
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val searchModuleNamed = "Search Module"
val searchModule = module {
    scope(named(searchModuleNamed)) {
        scoped<SearchApi> { (get() as Retrofit).create(SearchApi::class.java) }
        scoped<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(api = get()) }
        scoped<SearchRepository> { SearchRepositoryImpl(remoteDataSource = get()) }
        scoped<GetPhotosListUseCase> { SearchPhotosUseCaseImpl(repository = get()) }
        scoped { (scope: CoroutineScope) -> PhotosDataSource(scope, getPhotosListUseCase = get()) }
        scoped { (photoDataSource: PhotosDataSource) -> PhotosDataSourceFactory((photoDataSource)) }
        scoped<SearchCollectionsUseCase> { SearchCollectionsUseCaseImpl(repository = get()) }
        scoped { (scope: CoroutineScope) -> CollectionsDataSource(scope, useCase = get()) }
        scoped<SearchViewModel> { SearchViewModel() }
    }
}