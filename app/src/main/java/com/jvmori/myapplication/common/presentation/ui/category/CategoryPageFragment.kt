package com.jvmori.myapplication.common.presentation.ui.category


import androidx.fragment.app.Fragment
import com.jvmori.myapplication.databinding.CategoryPageBinding
import com.jvmori.myapplication.collectionslist.presentation.ui.CollectionsFragment
import com.jvmori.myapplication.photoslist.presentation.ui.PhotosFragment

open class CategoryPageFragment : Fragment() {

    private lateinit var binding: CategoryPageBinding
    private var id: Int? = -1

    companion object {
        fun newInstance(id: Int): CategoryPageFragment {
            return  when (id){
                0 -> PhotosFragment()
                1 -> CollectionsFragment()
                else -> CategoryPageFragment()
            }
        }
    }

    private fun bindCollections(){}
}
