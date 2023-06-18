package net.panacota.app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.usecases.getRecipesByType.GetRecipesByTypeUseCase
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val getRecipesByTypeUseCase: GetRecipesByTypeUseCase) : ViewModel() {
    val recipes = MutableLiveData<List<Recipe>>()
    private var launchedJob: Job? = null

    fun start(mealType: MealType) {
        launchedJob?.cancel("Change category")
        launchedJob = viewModelScope.launch(Dispatchers.IO) {
            val recipes = getRecipesByTypeUseCase(mealType)
            withContext(Dispatchers.Main) {
                this@CategoryViewModel.recipes.postValue(recipes)
            }
        }
    }
}