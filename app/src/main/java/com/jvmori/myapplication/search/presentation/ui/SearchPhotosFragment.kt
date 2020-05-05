package com.jvmori.myapplication.search.presentation.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchPhotosFragment : Fragment(R.layout.fragment_search_photos) {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val viewModel: SearchViewModel = searchScope.get<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            setPhotoParams(PhotoParams("sad", 1))
            getPhotos().observe(this@SearchPhotosFragment, Observer {
                Log.i("PHOTOS", it.toString())
            })
        }
    }
}
