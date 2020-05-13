package com.jvmori.myapplication.search.presentation.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.jvmori.myapplication.search.data.PhotoParams
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseSearchViewModel : ViewModel(), KoinComponent {
    protected val config by inject<PagedList.Config>()
    protected val photoParamsObservable = MutableLiveData<PhotoParams>()
    protected var photoParams = PhotoParams("", 1)

    fun setQuery(query: String?) {
        query?.let {
            if (it.isNotEmpty()){
                photoParams.query = it
                this.photoParamsObservable.value = photoParams
            }
        }
    }

    protected fun <T> getFactory(dataSource: DataSource<Int, T>): DataSource.Factory<Int, T> {
        return object : DataSource.Factory<Int, T>() {
            override fun create(): DataSource<Int, T> {
                return dataSource
            }
        }
    }
}