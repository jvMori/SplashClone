package com.jvmori.myapplication.photoslist.presentation.viewmodels

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase

class FakeGetPhotosListUseCase(
    var status: Resource.Status = Resource.Status.SUCCESS,
    var data: PhotoEntity = PhotoEntity("1", "", 1)
) : GetPhotosListUseCase {

    override suspend fun getPhotos(page: Int, order: String): Resource<List<PhotoEntity>> {
        return when (status) {
            is Resource.Status.SUCCESS -> Resource.success(listOf(data))
            Resource.Status.ERROR -> Resource.error("", null)
            Resource.Status.LOADING -> Resource.loading(null)
            Resource.Status.NETWORK_ERROR -> Resource.networkError(null, "")
        }
    }
}