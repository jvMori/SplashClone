package com.jvmori.myapplication.collectionslist.domain.usecases

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import kotlinx.coroutines.flow.Flow

interface GetCollectionsUseCase {
    fun getCollections(type : CollectionType): DataSource.Factory<Int, CollectionEntity>
}