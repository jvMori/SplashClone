package com.jvmori.myapplication.search.presentation.di

import com.jvmori.myapplication.search.data.remote.SearchApi
import com.jvmori.myapplication.search.data.remote.SearchRemoteDataSourceImpl
import com.jvmori.myapplication.search.data.repositories.SearchRepositoryImpl
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource
import com.jvmori.myapplication.search.domain.repositories.SearchRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val baseSearchModule = module {
    single<SearchApi> { (get() as Retrofit).create(SearchApi::class.java) }
    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(api = get()) }
    single<SearchRepository> { SearchRepositoryImpl(remoteDataSource = get()) }
}