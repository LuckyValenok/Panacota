package net.panacota.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.panacota.app.R
import net.panacota.app.databinding.CategoryItemBinding
import net.panacota.app.domain.data.Recipe
import net.panacota.app.ui.dialogs.RecipeDialog

class RecipesAdapter(list: List<Recipe>? = null) :
    ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder>(RecipeDiffUtil()) {
    init {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) = with(binding) {
            header.text = recipe.title
            Glide
                .with(binding.root)
                .load(recipe.image)
                .centerCrop()
                .placeholder(R.drawable.category_item_img)
                .into(backgroundImage)
            card.setOnClickListener {
                RecipeDialog.show((root.context as AppCompatActivity).supportFragmentManager, recipe)
            }
        }
    }

    class RecipeDiffUtil : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem == newItem

    }
}