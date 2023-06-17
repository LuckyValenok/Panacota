package net.panacota.app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import net.panacota.app.databinding.CategorySelectListBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.ui.adapters.CategorySelectAdapter

class CategorySelectDialog(private val selectCategory: MealType? = null, private val onClick: (MealType) -> Unit) :
    FullScreenDialog() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CategorySelectListBinding = CategorySelectListBinding.inflate(inflater, container, false)

        binding.buttonExit.setOnClickListener {
            dismiss()
        }

        binding.categories.adapter = CategorySelectAdapter(selectCategory) {
            onClick(it)
            dismiss()
        }

        return binding.root
    }

    companion object {
        fun show(fragmentManager: FragmentManager, selectedMealType: MealType? = null, onClick: (MealType) -> Unit) {
            val categorySelectDialog = CategorySelectDialog(selectedMealType, onClick)
            categorySelectDialog.show(fragmentManager, null)
        }
    }
}