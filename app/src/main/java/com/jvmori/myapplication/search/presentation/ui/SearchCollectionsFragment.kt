package com.jvmori.myapplication.search.presentation.ui

import android.util.Log
import androidx.fragment.app.Fragment
import com.jvmori.myapplication.R

class SearchCollectionsFragment : Fragment(R.layout.fragment_search_photos), SearchFragment.ISearchQuery{
    override fun search(query: String?) {
        Log.i("PHOTOS", "click")
    }
}