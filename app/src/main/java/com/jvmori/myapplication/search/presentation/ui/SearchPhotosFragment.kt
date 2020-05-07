package com.jvmori.myapplication.search.presentation.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.databinding.SearchPhotosBinding
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.presentation.ui.PhotosAdapter
import com.jvmori.myapplication.search.data.Orientation
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.viemodel.SearchViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchPhotosFragment : Fragment(R.layout.fragment_search_photos),
    SearchFragment.ISearchQuery,
    SearchFragment.ISpinnerItemSelected {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val viewModel: SearchViewModel = searchScope.get()
    private lateinit var binding: SearchPhotosBinding
    private lateinit var adapter: PhotosAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_photos,
            container,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter = PhotosAdapter.initGridAdapter(binding.recyclerView, this.requireContext())
        binding.showLoading(false)
        viewModel.apply {
            observePhotos()
            observeNetworkStatus()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (parentFragment as SearchFragment).spinnerItemSelected = this
    }

    override fun search(query: String?) {
        viewModel.setQuery(query)
    }

    override fun select(position: Int) {
        val orientation = if (position > 0) Orientation.values()[position].toString() else ""
        viewModel.setPhotoOrientation(orientation)
    }

    private fun SearchViewModel.observePhotos() {
        getPhotos().observe(this@SearchPhotosFragment, Observer {
            showResults(it)
        })
    }

    private fun SearchViewModel.observeNetworkStatus() {
        photosNetworkStatus.observe(this@SearchPhotosFragment, Observer {
            when (it) {
                Resource.Status.LOADING -> {
                    binding.apply {
                        showError(false, null)
                        showLoading(true)
                    }
                }
                Resource.Status.SUCCESS -> {
                    binding.apply {
                        showLoading(false)
                        showError(false, null)
                    }
                }
                Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> {
                    binding.apply {
                        showLoading(false)
                        showError(true) {
                            viewModel.getPhotosRetryAction()
                        }
                    }
                }
            }
        })
    }

    private fun SearchPhotosBinding.showError(visible: Boolean, retryAction: (() -> Unit)?) {
        this.errorLayout.visibility = if (visible) View.VISIBLE else View.GONE
        this.retryBtn.setOnClickListener {
            retryAction?.invoke()
        }
    }

    private fun SearchPhotosBinding.showLoading(visible: Boolean) {
        this.loadingLayout.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showResults(data: PagedList<PhotoEntity>) {
        binding.recyclerView.recycledViewPool.clear()
        adapter.apply {
            notifyDataSetChanged()
            submitList(data)
        }
    }
}
