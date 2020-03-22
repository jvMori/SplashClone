package com.jvmori.myapplication.collectionslist.presentation.di

import com.jvmori.myapplication.collectionslist.data.remote.CollectionsApi
import org.koin.dsl.module
import retrofit2.Retrofit

val collectionsModule = module {
    single<CollectionsApi> { (get() as Retrofit).create(CollectionsApi::class.java) }
}