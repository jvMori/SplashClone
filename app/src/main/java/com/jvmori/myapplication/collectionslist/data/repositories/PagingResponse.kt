package com.jvmori.myapplication.collectionslist.data.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.Resource

data class PagingResponse(
    val data : List<CollectionEntity>,
    val networkInfo : Resource.Status
)