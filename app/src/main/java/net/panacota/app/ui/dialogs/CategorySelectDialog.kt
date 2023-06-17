package net.panacota.app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import net.panacota.app.R
import net.panacota.app.databinding.CategorySelectListBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.ui.adapters.CategorySelectAdapter

class CategorySelectDialog(private val selectCategory: MealType? = null, private val onClick: (MealType) -> Unit) : DialogFragment() {
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

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Panacota_FullScreenDialog)
    }
}