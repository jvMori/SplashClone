package com.jvmori.myapplication.features.categories.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jvmori.myapplication.R
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.databinding.CategoryPageBinding
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import com.jvmori.myapplication.features.photolist.presentation.PhotosViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryPageFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by viewModel(PhotosViewModel::class)
    private lateinit var binding: CategoryPageBinding
    private var id: Int? = -1

    companion object {
        fun newInstance(id: Int): CategoryPageFragment {
            val fragment = CategoryPageFragment()
            val arg = Bundle()
            arg.putInt("id", id)
            fragment.arguments = arg
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        when (id){
            0 -> bindPhotos()
            1 -> bindCollections()
        }
    }

    private fun bindCollections(){}

    private fun bindPhotos() {
        photosViewModel.fetchPhotos()
        photosViewModel.photos.observe(this, Observer {
            when (it.status){
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> showSuccess(it.data)
                Resource.Status.ERROR -> showError(it.message)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<CategoryPageBinding>(
            inflater,
            R.layout.category_page,
            container,
            false
        ).apply {
            items = listOf()
        }
        return binding.root
    }

    private fun showLoading(){}
    private fun showSuccess(data : List<PhotoEntity>?){
        binding.items = data
        binding.textView.text = data?.get(0)?.url
    }
    private fun showError(errorMessage : String?){
        Log.i("PHOTOS", errorMessage ?: "")
    }
}
