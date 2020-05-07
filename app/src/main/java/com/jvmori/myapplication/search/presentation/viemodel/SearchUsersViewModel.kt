package com.jvmori.myapplication.search.presentation.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.search.data.repositories.SearchDataSource
import com.jvmori.myapplication.search.domain.entities.UserEntity
import com.jvmori.myapplication.search.presentation.di.searchUsersId
import com.jvmori.myapplication.search.presentation.di.searchUsersQualifier
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchUsersViewModel : BaseSearchViewModel() {

    private val usersScope = getKoin().getOrCreateScope(searchUsersId, named(searchUsersQualifier))
    private val userDataSource: SearchDataSource<UserEntity> = usersScope.get { parametersOf(viewModelScope) }

    fun getUsers(): LiveData<PagedList<UserEntity>> {
        return Transformations.switchMap(photoParamsObservable) {
            userDataSource.query = it.query
            LivePagedListBuilder<Int, UserEntity>(getFactory(userDataSource), config).build()
        }
    }

    fun getRetryAction() {
        userDataSource.retryAction?.invoke()
    }

    val networkStatus: LiveData<Resource.Status> by lazy {
        userDataSource.networkStatus
    }
}