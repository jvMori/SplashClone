package com.jvmori.myapplication.search.presentation.di

import com.jvmori.myapplication.search.data.repositories.SearchDataSource
import com.jvmori.myapplication.search.data.usecases.SearchUsersUseCaseImpl
import com.jvmori.myapplication.users.domain.UserEntity
import com.jvmori.myapplication.search.domain.usecases.SearchUsersUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val searchUsersQualifier = "UsersModule"
const val searchUsersId = "users"
val usersModule = module {
    scope(named(searchUsersQualifier)) {
        scoped<SearchUsersUseCase> { SearchUsersUseCaseImpl(repository = get()) }
        scoped { (scope: CoroutineScope) -> SearchDataSource<UserEntity>(scope, useCase = get() as SearchUsersUseCase) }
    }
}