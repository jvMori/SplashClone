package com.jvmori.myapplication.collectionslist.domain.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource

interface GetFeaturedCollectionsUseCase {
    suspend fun getFeaturedCollections(page : Int) : Resource<List<CollectionEntity>>
}