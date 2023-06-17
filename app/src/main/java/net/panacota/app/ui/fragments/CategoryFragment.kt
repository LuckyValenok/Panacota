package net.panacota.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.panacota.app.MainApplication
import net.panacota.app.databinding.CategoryFragmentBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.ui.adapters.RecipesAdapter
import net.panacota.app.ui.dialogs.CategorySelectDialog
import net.panacota.app.ui.viewmodels.CategoryViewModel
import javax.inject.Inject


class CategoryFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var categoryViewModel: CategoryViewModel
    private var mealType: MealType = MealType.MAIN_COURSE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as MainApplication).component.injectCategoryFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CategoryFragmentBinding = CategoryFragmentBinding.inflate(inflater, container, false)
        val args: CategoryFragmentArgs by navArgs()
        mealType = args.mealType

        val adapter = RecipesAdapter()
        binding.apply {
            allItems.apply {
                back.setOnClickListener {
                    val navController = findNavController()
                    if (!navController.popBackStack()) {
                        val action = CategoryFragmentDirections.actionCategoryFragmentToMainFragment()
                        navController.navigate(action)
                    }
                }
                category.text = resources.getString(mealType.getStringResource())
                recipes.adapter = adapter
            }
            navbar.categoryButton.setOnClickListener {
                CategorySelectDialog.show(parentFragmentManager, mealType) {
                    if (mealType == it) {
                        allItems.back.callOnClick()
                    } else {
                        mealType = it
                        allItems.category.text = resources.getString(it.getStringResource())
                        categoryViewModel.start(it)
                    }
                }
            }
        }

        categoryViewModel = viewModelFactory.create(CategoryViewModel::class.java)

        categoryViewModel.recipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        categoryViewModel.start(mealType)

        return binding.root
    }
}