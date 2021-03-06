package com.jvmori.myapplication.search.data.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.users.domain.UserEntity
import com.jvmori.myapplication.search.domain.repositories.SearchRepository
import com.jvmori.myapplication.search.domain.usecases.SearchUsersUseCase

class SearchUsersUseCaseImpl(
    private val repository: SearchRepository
) : SearchUsersUseCase {
    override suspend fun fetchData(query: String, page: Int): Resource<List<UserEntity>> {
        return repository.searchUsers(query, page)
    }
}