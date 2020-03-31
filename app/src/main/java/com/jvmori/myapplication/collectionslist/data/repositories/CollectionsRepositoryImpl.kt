package com.jvmori.myapplication.collectionslist.data.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.common.data.Resource

class CollectionsRepositoryImpl :
    CollectionsRepository {
    override suspend fun getCollections(page: Int): Resource<List<CollectionEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getFeaturedCollections(page: Int): Resource<List<CollectionEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}