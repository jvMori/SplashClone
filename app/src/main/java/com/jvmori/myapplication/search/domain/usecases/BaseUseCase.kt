package com.jvmori.myapplication.search.domain.usecases

import com.jvmori.myapplication.common.data.remote.Resource

interface BaseUseCase<T> {
    suspend fun fetchData(query: String, page: Int): Resource<T>
}