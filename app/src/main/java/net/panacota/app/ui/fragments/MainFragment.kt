package net.panacota.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.search.SearchBar
import net.panacota.app.MainActivity
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
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: CategoriesAdapter

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

        (requireActivity() as MainActivity).binding.apply {
            categoryButton.setOnClickListener {
                CategorySelectDialog.show(parentFragmentManager, onClick = onClick)
            }
        }

        adapter = CategoriesAdapter {
            onClick(it.type)
        }
        binding.categories.adapter = adapter

        mainViewModel = viewModelFactory.create(MainViewModel::class.java)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainViewModel.categories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyItemChanged(it.size - 1)
        }
    }
}