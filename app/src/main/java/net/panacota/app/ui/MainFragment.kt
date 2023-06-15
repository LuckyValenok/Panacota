package net.panacota.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.panacota.app.MainApplication
import net.panacota.app.databinding.MainFragmentBinding
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

        val adapter = CategoriesAdapter()
        binding.categories.adapter = adapter

        val mainViewModel = viewModelFactory.create(MainViewModel::class.java)

        mainViewModel.categories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}