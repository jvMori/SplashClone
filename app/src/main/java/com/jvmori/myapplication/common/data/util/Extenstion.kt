package com.jvmori.myapplication.common.data.util

import androidx.paging.DataSource

fun <T> getFactory(dataSource: DataSource<Int, T>): DataSource.Factory<Int, T> {
    return object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return dataSource
        }
    }
}