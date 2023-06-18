package net.panacota.app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.FragmentManager
import net.panacota.app.databinding.FilterListBinding
import net.panacota.app.domain.repository.SharedPreferencesRepository
import net.panacota.app.ui.adapters.FiltersAdapter

class FiltersDialog(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val update: () -> Unit
) : FullScreenDialog() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FilterListBinding = FilterListBinding.inflate(inflater, container, false)

        val adapter = FiltersAdapter(requireContext(), sharedPreferencesRepository)

        binding.filters.adapter = adapter

        binding.btnApply.setOnClickListener {
            update()
            dismiss()
        }

        return binding.root
    }

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            sharedPreferencesRepository: SharedPreferencesRepository,
            update: () -> Unit
        ) {
            val categorySelectDialog = FiltersDialog(sharedPreferencesRepository, update)
            categorySelectDialog.show(fragmentManager, null)
        }
    }
}