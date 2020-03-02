package com.jvmori.myapplication.photoslist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jvmori.myapplication.R
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.databinding.PhotosFragmentBinding
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PhotosFragment : CategoryPageFragment() {

    private val photosViewModel: PhotosViewModel by sharedViewModel()

    private lateinit var binding: PhotosFragmentBinding
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<PhotosFragmentBinding>(
            inflater,
            R.layout.photos_fragment,
            container,
            false
        ).apply {
            //items = listOf()
        }
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        photosViewModel.apply {
            changeOrder(Order.popular)
        }
        initPhotosRecyclerView()
        bindPhotos()
        observeNetworkStatus()
    }

    private fun bindPhotos() {
        photosViewModel.photos.observe(this, Observer {
            binding.photosRecyclerView.recycledViewPool.clear()
            photosAdapter.notifyDataSetChanged()
            showSuccess(it)
        })
    }

    private fun observeNetworkStatus() {
//        photosViewModel.networkStatus.observe(this, Observer {
//            photosAdapter.setState(it)
//        })
    }

    private fun showSuccess(data: PagedList<PhotoEntity>?) {
        photosAdapter.submitList(data)
    }

    private fun initPhotosRecyclerView() {
        photosAdapter = PhotosAdapter()
        binding.photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = photosAdapter
        }
    }
}