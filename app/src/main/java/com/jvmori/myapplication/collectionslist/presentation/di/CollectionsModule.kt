package com.jvmori.myapplication.collectionslist.presentation.di

import com.jvmori.myapplication.collectionslist.data.local.CollectionsDao
import com.jvmori.myapplication.collectionslist.data.local.LocalCollectionsDataSourceImpl
import com.jvmori.myapplication.collectionslist.data.remote.CollectionsApi
import com.jvmori.myapplication.collectionslist.data.remote.RemoteCollectionsDataSourceImpl
import com.jvmori.myapplication.collectionslist.data.repositories.CollectionsRepositoryImpl
import com.jvmori.myapplication.collectionslist.data.usecases.GetCollectionsUseCaseImpl
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.PhotosDatabase
import org.koin.dsl.module
import retrofit2.Retrofit

val collectionsModule = module {
    single<CollectionsApi> { (get() as Retrofit).create(CollectionsApi::class.java) }
    single<CollectionsDao> { (get() as PhotosDatabase).collectionsDao() }
    single<RemoteCollectionsDataSource> { RemoteCollectionsDataSourceImpl(get()) }
    single<LocalCollectionsDataSource> { LocalCollectionsDataSourceImpl(get()) }
    single<CollectionsRepository> { CollectionsRepositoryImpl(get(), get()) }
    single<GetCollectionsUseCase> { GetCollectionsUseCaseImpl(get()) }
}