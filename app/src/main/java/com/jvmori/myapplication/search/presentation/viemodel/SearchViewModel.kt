package com.jvmori.myapplication.search.presentation.viemodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import org.koin.android.ext.android.getKoin
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchViewModel : ViewModel(), KoinComponent {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))

    private val config by inject<PagedList.Config>()
    private val photosDataSource: PhotosDataSource = searchScope.get { parametersOf(viewModelScope) }
    private val factory: PhotosDataSourceFactory = searchScope.get { parametersOf(photosDataSource) }

    private val photoParams = MutableLiveData<PhotoParams>()
    private var _photoParams = PhotoParams("", 1)

    fun setPhotoParams(photoParams: PhotoParams){
        _photoParams = photoParams
        this.photoParams.value = photoParams
    }

    fun setPhotoQuery(query : String?){
        query?.let{
            _photoParams.query = it
            this.photoParams.value = _photoParams
        }
    }

    fun getPhotos() : LiveData<PagedList<PhotoEntity>>{
        return Transformations.switchMap(photoParams){
            factory.setPhotoParams(it)
            LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    fun retryAction() {
        photosDataSource.retryAction?.invoke()
    }

    val networkStatus: LiveData<Resource.Status> by lazy {
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }
}