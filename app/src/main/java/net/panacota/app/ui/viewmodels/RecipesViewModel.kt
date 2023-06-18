package net.panacota.app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.repository.SharedPreferencesRepository
import net.panacota.app.domain.usecases.getRecipesByFilters.GetRecipesByFiltersUseCase
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    private val getRecipesByFiltersUseCase: GetRecipesByFiltersUseCase,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : EndlessOnScrollViewModel<Recipe, MealType>() {
    override suspend fun getResult(arg: MealType?, offset: Int): List<Recipe> =
        getRecipesByFiltersUseCase(
            mapOf(
                "type" to arg.toString()
            ).plus(
                sharedPreferencesRepository.getAllPairs()
                    .map { pair -> Pair(pair.first, pair.second.toString()) }
            ),
            30,
            offset
        )

    override fun onCancel(liveData: MutableLiveData<List<Recipe>>) {}

    override fun checkArgument(arg: MealType?): Boolean = true
}