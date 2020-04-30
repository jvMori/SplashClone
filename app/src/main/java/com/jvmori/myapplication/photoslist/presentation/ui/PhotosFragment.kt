package com.jvmori.myapplication.photoslist.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.presentation.ui.category.CategoryPageFragment
import com.jvmori.myapplication.databinding.PhotosFragmentBinding
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PhotosFragment : CategoryPageFragment() {

    private val photosViewModel: PhotosViewModel by sharedViewModel()

    private lateinit var binding: PhotosFragmentBinding
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<PhotosFragmentBinding>(
            inflater,
            R.layout.photos_fragment,
            container,
            false
        )
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        photosViewModel.apply {
            changeOrder(Order.latest)
        }
        initPhotosRecyclerView()
        bindPhotos()
        observeNetworkStatus()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.photos_menu, menu)
//        (menu as MenuBuilder).setOptionalIconsVisible(true)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.latest -> {
//                photosViewModel.changeOrder(Order.latest)
//                true
//            }
//            R.id.popular -> {
//                photosViewModel.changeOrder(Order.popular)
//                true
//            }
//            R.id.oldest -> {
//                photosViewModel.changeOrder(Order.oldest)
//                true
//            }
//            R.id.searchIcon -> {
//                true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    private fun bindPhotos() {
        photosViewModel.fetchPhotos().observe(this, Observer {
            binding.photosRecyclerView.recycledViewPool.clear()
            photosAdapter.notifyDataSetChanged()
            showSuccess(it)
        })
    }

    private fun observeNetworkStatus() {
        photosViewModel.networkStatus.observe(this, Observer {
            when (it){
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
        binding.retryPanel.visibility = View.VISIBLE
        binding.retryBtn.setOnClickListener {
            photosViewModel.retryAction()
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

    private fun initPhotosRecyclerView() {
        photosAdapter = PhotosAdapter(this.requireContext())
        binding.photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = photosAdapter
        }
    }
}