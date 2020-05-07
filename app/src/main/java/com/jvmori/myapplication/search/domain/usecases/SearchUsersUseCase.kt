package com.jvmori.myapplication.search.domain.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.search.domain.entities.UserEntity

interface SearchUsersUseCase {
    suspend fun searchUsers(query: String, page: Int): Resource<List<UserEntity>>
}