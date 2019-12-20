package com.jvmori.myapplication.features.categories.presentation


import androidx.fragment.app.Fragment
import com.jvmori.myapplication.databinding.CategoryPageBinding
import com.jvmori.myapplication.features.CollectionsFragment
import com.jvmori.myapplication.features.photolist.presentation.PhotosFragment

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
