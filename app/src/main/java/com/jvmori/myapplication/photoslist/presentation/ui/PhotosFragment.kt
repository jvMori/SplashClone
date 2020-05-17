package com.jvmori.myapplication.photoslist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.paging.PagedList
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.databinding.SearchPhotosBinding
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PhotosFragment : CategoryPageFragment() {

    private val photosViewModel: PhotosViewModel by sharedViewModel()
    private lateinit var binding: SearchPhotosBinding
    private lateinit var photosAdapter: PhotosAdapter

    private var collectionId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_photos,
            container,
            false
        )
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        collectionId = getCollectionId()
        photosViewModel.apply {
            changeOrder(Order.latest)
            fetchPhotos(collectionId)
        }
        initPhotosAdapter()
        bindPhotos()
        observeNetworkStatus()
    }

    private fun getCollectionId(): Int? {
        return try {
            navArgs<PhotosFragmentArgs>().value.collectionId
        } catch (e: Exception) {
            null
        }
    }

    private fun bindPhotos() {
        photosViewModel.photos?.observe(this, Observer {
            binding.recyclerView.recycledViewPool.clear()
            photosAdapter.notifyDataSetChanged()
            showSuccess(it)
        })
    }

    private fun observeNetworkStatus() {
        photosViewModel.networkStatus.observe(this, Observer {
            when (it) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> showError()
                Resource.Status.SUCCESS -> hideLoading()
            }
        })
    }

    private fun hideLoading() {
        binding.loadingLayout.visibility = View.GONE
    }

    private fun showError() {
        hideLoading()
        binding.errorLayout.visibility = View.VISIBLE
        binding.retryBtn.setOnClickListener {
            photosViewModel.retryAction(null)
            it.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    private fun showSuccess(data: PagedList<PhotoEntity>?) {
        hideLoading()
        photosAdapter.submitList(data)
    }

    private fun initPhotosAdapter() {
        photosAdapter = PhotosAdapter.initGridAdapter(binding.recyclerView, this.requireContext())
    }
}