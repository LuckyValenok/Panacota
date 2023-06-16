package net.panacota.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import net.panacota.app.MainApplication
import net.panacota.app.databinding.MainFragmentBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.ui.adapters.CategoriesAdapter
import net.panacota.app.ui.dialogs.CategorySelectDialog
import net.panacota.app.ui.viewmodels.MainViewModel
import javax.inject.Inject

class MainFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as MainApplication).component.injectMainFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)

        val onClick = { mealType: MealType ->
            val action = MainFragmentDirections.actionMainFragmentToCategoryFragment(mealType)
            findNavController().navigate(action)
        }
        val adapter = CategoriesAdapter {
            onClick(it.type)
        }
        binding.apply {
            categories.adapter = adapter
            navbar.categoryButton.setOnClickListener {
                val categorySelectDialog = CategorySelectDialog(onClick = onClick)
                categorySelectDialog.show(this@MainFragment.parentFragmentManager, null)
            }
        }

        val mainViewModel = viewModelFactory.create(MainViewModel::class.java)

        mainViewModel.categories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}