package com.jvmori.myapplication.presentation.ui.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR.photoData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jvmori.myapplication.R
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.databinding.PhotosFragmentBinding
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.presentation.viewmodels.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PhotosFragment : CategoryPageFragment() {

    private val photosViewModel: PhotosViewModel by viewModel(
        PhotosViewModel::class
    )

    private lateinit var binding: PhotosFragmentBinding
    private lateinit var  photosAdapter : PhotosAdapter

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

    override fun onStart() {
        super.onStart()
        initPhotosRecyclerView()
        bindPhotos()
    }

    private fun bindPhotos() {
        photosViewModel.photos.observe(this, Observer {
            showSuccess(it)
        })
    }

    private fun showLoading() {}
    private fun showSuccess(data: PagedList<PhotoEntity>?) {
        photosAdapter.submitList(data)
    }

    private fun initPhotosRecyclerView() {
        photosAdapter = PhotosAdapter()
        binding.photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            //layoutManager = LinearLayoutManager(this@PhotosFragment.requireContext(), RecyclerView.VERTICAL, false)
            adapter = photosAdapter
        }
    }

    private fun showError(errorMessage: String?) {
        Log.i("PHOTOS", errorMessage ?: "")
    }
}