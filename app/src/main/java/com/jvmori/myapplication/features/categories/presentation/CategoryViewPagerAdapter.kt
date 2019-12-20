package com.jvmori.myapplication.features.categories.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jvmori.myapplication.features.categories.data.Category

class CategoryViewPagerAdapter(
    fragment: Fragment,
    var items : List<Category>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return CategoryPageFragment.newInstance(position).apply {
            items = this@CategoryViewPagerAdapter.items
        }
    }
}