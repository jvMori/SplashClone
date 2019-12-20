package com.jvmori.myapplication.features.categories.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jvmori.myapplication.R
import com.jvmori.myapplication.databinding.CategoryPageBinding
import com.jvmori.myapplication.features.categories.data.Category

class CategoryPageFragment : Fragment() {

    lateinit var items: List<Category>
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<CategoryPageBinding>(
            inflater,
            R.layout.category_page,
            container,
            false
        ).apply {
            category = this@CategoryPageFragment.items[id ?: 0]
        }
        return binding.root
    }
}
