package com.jvmori.myapplication.collectionslist.presentation.di

import com.jvmori.myapplication.collectionslist.data.local.CollectionsDao
import com.jvmori.myapplication.collectionslist.data.local.LocalCollectionsDataSourceImpl
import com.jvmori.myapplication.collectionslist.data.remote.CollectionsApi
import com.jvmori.myapplication.collectionslist.data.remote.RemoteCollectionsDataSourceImpl
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.PhotosDatabase
import com.jvmori.myapplication.photoslist.data.remote.RemotePhotosDataSourceImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val collectionsModule = module {
    single<CollectionsApi> { (get() as Retrofit).create(CollectionsApi::class.java) }
    single<CollectionsDao> { (get() as PhotosDatabase).collectionsDao() }
    single<RemoteCollectionsDataSource> { RemoteCollectionsDataSourceImpl(get()) }
    single<LocalCollectionsDataSource> { LocalCollectionsDataSourceImpl(get()) }
}