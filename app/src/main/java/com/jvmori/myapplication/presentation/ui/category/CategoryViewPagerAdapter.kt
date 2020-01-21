package com.jvmori.myapplication.presentation.ui.category

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jvmori.myapplication.presentation.ui.category.CategoryPageFragment

class CategoryViewPagerAdapter(
    fragment: Fragment,
    private var size: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        return CategoryPageFragment.newInstance(position)
    }
}