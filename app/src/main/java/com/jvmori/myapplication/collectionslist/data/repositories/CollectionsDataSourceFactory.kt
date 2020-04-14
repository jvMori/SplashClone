package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity

class CollectionsDataSourceFactory(
    private val collectionsDataSource: CollectionsDataSource
) : DataSource.Factory<Int, CollectionEntity>() {

    override fun create(): DataSource<Int, CollectionEntity> {
        return collectionsDataSource
    }
}