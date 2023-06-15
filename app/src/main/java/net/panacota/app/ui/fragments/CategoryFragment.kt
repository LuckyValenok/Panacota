package net.panacota.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import net.panacota.app.MainApplication
import net.panacota.app.databinding.CategoryFragmentBinding
import net.panacota.app.databinding.MainFragmentBinding
import net.panacota.app.ui.adapters.CategoriesAdapter
import net.panacota.app.ui.adapters.RecipesAdapter
import net.panacota.app.ui.viewmodels.CategoryViewModel
import net.panacota.app.ui.viewmodels.MainViewModel
import javax.inject.Inject

class CategoryFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val args: CategoryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as MainApplication).component.injectCategoryFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CategoryFragmentBinding = CategoryFragmentBinding.inflate(inflater, container, false)

        val mealType = args.mealType
        binding.category.text = resources.getString(mealType.getStringResource())

        val adapter = RecipesAdapter()
        binding.recipes.adapter = adapter

        val categoryViewModel = viewModelFactory.create(CategoryViewModel::class.java)

        categoryViewModel.recipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        categoryViewModel.start(mealType)

        return binding.root
    }
}