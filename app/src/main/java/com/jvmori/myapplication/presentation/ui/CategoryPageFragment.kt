package com.jvmori.myapplication.presentation.ui


import androidx.fragment.app.Fragment
import com.jvmori.myapplication.databinding.CategoryPageBinding

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
