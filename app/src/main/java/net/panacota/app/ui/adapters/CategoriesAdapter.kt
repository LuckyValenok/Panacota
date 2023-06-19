package net.panacota.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.panacota.app.databinding.CategoryListBinding
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe

class CategoriesAdapter(private val onClick: (Category) -> Unit) :
    ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            CategoryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: CategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) = with(binding) {
            categoryName.text = root.resources.getString(category.type.getStringResource())
            recyclerInfo.adapter = RecipesAdapter(root.context, category.list)
            categoryButtonMore.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }
    }

    class CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }
}

data class Category(val type: MealType, val list: List<Recipe>)