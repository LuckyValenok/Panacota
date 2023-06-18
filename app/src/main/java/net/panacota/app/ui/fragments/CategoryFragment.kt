package net.panacota.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.panacota.app.MainActivity
import net.panacota.app.MainApplication
import net.panacota.app.databinding.CategoryAllItemsBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.ui.adapters.RecipesAdapter
import net.panacota.app.ui.dialogs.CategorySelectDialog
import net.panacota.app.ui.dialogs.FiltersDialog
import net.panacota.app.ui.listeners.EndlessRecyclerOnScrollListener
import net.panacota.app.ui.viewmodels.CategoryViewModel
import javax.inject.Inject


class CategoryFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as MainApplication).component.injectCategoryFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CategoryAllItemsBinding =
            CategoryAllItemsBinding.inflate(inflater, container, false)
        val args: CategoryFragmentArgs by navArgs()
        var mealType: MealType? =
            if (args.mealType != null) MealType.valueOf(args.mealType!!) else null

        (requireActivity() as MainActivity).apply {
            this.binding.categoryButton.setOnClickListener {
                CategorySelectDialog.show(parentFragmentManager, mealType) {
                    if (mealType == it) {
                        binding.back.callOnClick()
                    } else {
                        mealType = it
                        binding.category.text = resources.getString(it.getStringResource())
                        categoryViewModel.load(it)
                    }
                }
            }
            this.binding.filterButton.setOnClickListener {
                FiltersDialog.show(parentFragmentManager, sharedPreferencesRepository) {
                    categoryViewModel.load(mealType)
                }
            }
        }

        val adapter = RecipesAdapter(requireContext()) {
            it.root.layoutParams.width = LayoutParams.MATCH_PARENT
        }
        binding.apply {
            back.setOnClickListener {
                val navController = findNavController()
                if (!navController.popBackStack()) {
                    val action = CategoryFragmentDirections.actionCategoryFragmentToMainFragment()
                    navController.navigate(action)
                }
            }
            category.text =
                if (mealType != null) resources.getString(mealType!!.getStringResource()) else ""
            recipes.adapter = adapter
            recipes.addOnScrollListener(EndlessRecyclerOnScrollListener {
                categoryViewModel.loadMore()
            })
        }

        categoryViewModel = viewModelFactory.create(CategoryViewModel::class.java)

        categoryViewModel.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        categoryViewModel.load(mealType)

        return binding.root
    }
}