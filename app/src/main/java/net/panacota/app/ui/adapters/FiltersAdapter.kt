package net.panacota.app.ui.adapters

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import net.panacota.app.R
import net.panacota.app.databinding.FilterBinding
import net.panacota.app.domain.data.Cuisine
import net.panacota.app.domain.data.Diet
import net.panacota.app.domain.data.IResourced
import net.panacota.app.domain.data.Intolerance
import net.panacota.app.domain.repository.SharedPreferencesRepository

class FiltersAdapter(
    private val context: Context,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) :
    RecyclerView.Adapter<FiltersAdapter.FilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder =
        FilterViewHolder(
            FilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = FILTERS.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(FILTERS[position])
    }

    inner class FilterViewHolder(private val binding: FilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filter: Filter<*>) = with(binding) {
            menu.hint = root.resources.getString(filter.getName())
            sharedPreferencesRepository.readData(filter.getApiKey())?.let { value ->
                menu.editText?.text = Editable.Factory.getInstance().newEditable(value)
            }
            val list: MutableList<String> =
                filter.getValuesResources().map { root.resources.getString(it) }.toMutableList()
            list.add(0, root.resources.getString(R.string.not_selected))
            val arrayAdapter = ArrayAdapter(
                context,
                R.layout.filter_item,
                list
            )
            autoCompleteText.setAdapter(arrayAdapter)
            menu.editText?.addTextChangedListener {
                sharedPreferencesRepository.saveData(filter.getApiKey(), it?.toString())
            }
        }
    }

    companion object {
        val FILTERS = listOf(
            Filter(R.string.cuisines, Cuisine.ARRAY, "cuisine"),
            Filter(R.string.diets, Diet.ARRAY, "diet"),
            Filter(R.string.intolerances, Intolerance.ARRAY, "intolerance")
        )
    }

    class Filter<T : IResourced>(private val name: Int, private val values: Array<T>, private val apiKey: String) {
        fun getName(): Int = name

        fun getValuesResources(): List<Int> = values.map { it.getStringResource() }

        fun getApiKey(): String = apiKey
    }
}