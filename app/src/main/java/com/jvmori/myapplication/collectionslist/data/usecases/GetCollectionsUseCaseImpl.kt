package com.jvmori.myapplication.collectionslist.data.usecases

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.remote.Resource

class GetCollectionsUseCaseImpl(private val repository: CollectionsRepository) :
    GetCollectionsUseCase {
    override fun getCollections(type : CollectionType): DataSource.Factory<Int, CollectionEntity> {
        return repository.fetchLocalCollections(type)
    }
}