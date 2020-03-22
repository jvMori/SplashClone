package com.jvmori.myapplication.collectionslist.domain.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.Resource

interface GetCollectionsUseCase {
    suspend fun getCollections(page : Int) : Resource<List<CollectionEntity>>
}