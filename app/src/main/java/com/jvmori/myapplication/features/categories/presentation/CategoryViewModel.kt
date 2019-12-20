package com.jvmori.myapplication.features.categories.presentation

import androidx.lifecycle.ViewModel
import com.jvmori.myapplication.features.categories.data.Category
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CategoryViewModel : ViewModel() {

    fun getCategories(photos: List<PhotoEntity>?, collections : List<PhotoEntity>): List<Category> {
        return listOf(
            Category(1, "Photos", photos),
            Category(2, "Collections", collections)
        )
    }
}

val categoryModule = module {
    viewModel { CategoryViewModel() }
}