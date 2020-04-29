package com.jvmori.myapplication.common.presentation.di

import androidx.paging.PagedList
import org.koin.dsl.module

val pagingModule = module {
    single {
        PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setPrefetchDistance(10 * 3)
            .setEnablePlaceholders(false)
            .build()
    }
}