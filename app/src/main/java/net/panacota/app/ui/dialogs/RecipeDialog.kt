package net.panacota.app.ui.dialogs

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import net.panacota.app.R
import net.panacota.app.databinding.RecipeCardBinding
import net.panacota.app.domain.data.Recipe

class RecipeDialog : FullScreenDialog() {
    private lateinit var binding: RecipeCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeCardBinding.inflate(inflater, container, false)

        val recipe = arguments?.getSerializable(RECIPE, Recipe::class.java)
        if (recipe != null) {
            bind(recipe)
        }

        return binding.root
    }

    private fun bind(recipe: Recipe) {
        binding.apply {
            Glide
                .with(root)
                .load(recipe.image)
                .centerCrop()
                .placeholder(R.drawable.receipt_card_image)
                .into(recipeImg)
            buttonExit.setOnClickListener {
                dismiss()
            }
            scrollToTop.setOnClickListener {
                scrollView.smoothScrollTo(0, 0)
                appBar.setExpanded(true)
            }
            scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
                if (scrollView.canScrollVertically(-1)) {
                    scrollToTop.visibility = View.VISIBLE
                } else {
                    scrollToTop.visibility = View.GONE
                }
            }

            recipeTitle.text = recipe.title
            recipeSummary.text = recipe.toSpannableStringBuilder(resources)
            recipeSummary.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    companion object {
        const val RECIPE = "RECIPE"

        fun show(fragmentManager: FragmentManager, recipe: Recipe) {
            val recipeDialog = RecipeDialog().apply {
                arguments = bundleOf(
                    RECIPE to recipe
                )
            }
            recipeDialog.show(fragmentManager, null)
        }
    }
}