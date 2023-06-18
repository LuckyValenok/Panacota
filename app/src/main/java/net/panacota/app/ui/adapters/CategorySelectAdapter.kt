package net.panacota.app.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.panacota.app.databinding.CategorySelectListItemBinding
import net.panacota.app.domain.data.MealType

class CategorySelectAdapter(
    private val selectCategory: MealType?,
    private val onClick: (MealType) -> Unit
) : RecyclerView.Adapter<CategorySelectAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            CategorySelectListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = MealType.ARRAY.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(MealType.ARRAY[position])
    }

    inner class CategoryViewHolder(private val binding: CategorySelectListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mealType: MealType) = with(binding) {
            root.text = root.resources.getString(mealType.getStringResource())
            if (mealType == selectCategory) {
                root.paintFlags = root.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
            root.setOnClickListener {
                onClick(mealType)
            }
        }
    }

}