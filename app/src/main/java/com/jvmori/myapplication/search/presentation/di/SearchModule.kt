package com.jvmori.myapplication.search.presentation.di

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.search.data.repositories.SearchDataSource
import com.jvmori.myapplication.search.data.usecases.SearchCollectionsUseCaseImpl
import com.jvmori.myapplication.search.data.usecases.SearchPhotosUseCaseImpl
import com.jvmori.myapplication.search.domain.usecases.SearchCollectionsUseCase
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val searchModuleNamed = "Search Module"
val searchModule = module {
    scope(named(searchModuleNamed)) {
        scoped<GetPhotosListUseCase> { SearchPhotosUseCaseImpl(repository = get()) }
        scoped { (scope: CoroutineScope) -> PhotosDataSource(scope, getPhotosListUseCase = get()) }
        scoped { (photoDataSource: PhotosDataSource) -> PhotosDataSourceFactory((photoDataSource)) }
        scoped<SearchCollectionsUseCase> { SearchCollectionsUseCaseImpl(repository = get()) }
        scoped { (scope: CoroutineScope) -> SearchDataSource<CollectionEntity>(scope, useCase = (get() as SearchCollectionsUseCase)) }
        scoped<SearchViewModel> { SearchViewModel() }
    }
}