package com.jvmori.myapplication.common.presentation.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.jvmori.myapplication.R
import com.jvmori.myapplication.common.presentation.ui.category.CategoryViewPagerAdapter
import com.jvmori.myapplication.databinding.Photos
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import kotlinx.android.synthetic.main.collection_item.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by sharedViewModel<PhotosViewModel>()
    private lateinit var binding: Photos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<Photos>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ).apply {
            viewModel = photosViewModel
            lifecycleOwner = this@HomeFragment
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).apply {
            if (this is MainActivity) {
                setSupportActionBar(binding.toolbar)
                setupActionBarWithNavController(navController, appBarConfiguration)
                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    title = when (destination.id){
                        R.id.homeFragment2 -> getString(R.string.app_title)
                        R.id.searchFragment -> ""
                        else -> "Splash"
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photos_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter =
            CategoryViewPagerAdapter(this, 2)
        binding.photosViewPager.apply {
            adapter = pagerAdapter
            setPageTransformer(ZoomOutPageTransformer())
        }
        TabLayoutMediator(binding.tabs, binding.photosViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "PHOTOS"
                1 -> "COLLECTIONS"
                else -> ""
            }
        }.attach()
    }
}
