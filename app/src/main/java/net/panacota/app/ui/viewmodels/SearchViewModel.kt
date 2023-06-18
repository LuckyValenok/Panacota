package net.panacota.app.ui.viewmodels

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.usecases.getRecipesByFilters.GetRecipesByFiltersUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getRecipesByFiltersUseCase: GetRecipesByFiltersUseCase
) : EndlessOnScrollViewModel<Recipe, Editable>() {
    override suspend fun getResult(arg: Editable?, offset: Int): List<Recipe> =
        getRecipesByFiltersUseCase(
            mapOf(
                "query" to arg.toString()
            ),
            30,
            offset
        )

    override fun onCancel(liveData: MutableLiveData<List<Recipe>>) =
        liveData.postValue(emptyList())

    override fun checkArgument(arg: Editable?): Boolean =
        arg != null && arg.trim().isNotEmpty()
}