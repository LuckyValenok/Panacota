package net.panacota.app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import net.panacota.app.databinding.CategorySelectListBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.ui.adapters.CategorySelectAdapter

class CategorySelectDialog(private val onClick: (MealType) -> Unit) : FullScreenDialog() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CategorySelectListBinding = CategorySelectListBinding.inflate(inflater, container, false)

        binding.buttonExit.setOnClickListener {
            dismiss()
        }

        val mealType: MealType? = arguments?.getString(SELECTED_MEAL_TYPE)?.let { MealType.valueOf(it) }
        binding.categories.adapter = CategorySelectAdapter(mealType) {
            onClick(it)
            dismiss()
        }

        return binding.root
    }

    companion object {
        const val SELECTED_MEAL_TYPE = "selectedMealType"

        fun show(fragmentManager: FragmentManager, selectedMealType: MealType? = null, onClick: (MealType) -> Unit) {
            val categorySelectDialog = CategorySelectDialog(onClick).apply {
                arguments = bundleOf(
                    SELECTED_MEAL_TYPE to selectedMealType?.name
                )
            }
            categorySelectDialog.show(fragmentManager, null)
        }
    }
}