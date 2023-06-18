package net.panacota.app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.usecases.getRecipesByType.GetRecipesByTypeUseCase
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
        private val getRecipesByTypeUseCase: GetRecipesByTypeUseCase
) : ViewModel() {
    val recipesLiveData = MutableLiveData<List<Recipe>>(mutableListOf())
    private var launchedJob: Job? = null
    private var launchedLoadMoreJob: Job? = null
    private var lastLoadMoreCounts: Int = 0
    private var size: Int = 0
    private lateinit var mealType: MealType

    fun start(mealType: MealType) {
        this.mealType = mealType
        launchedJob?.cancel("Change category")
        launchedJob = viewModelScope.launch(Dispatchers.IO) {
            val recipes = getRecipesByTypeUseCase(mealType, 30)
            lastLoadMoreCounts = recipes.size
            size += lastLoadMoreCounts
            withContext(Dispatchers.Main) {
                recipesLiveData.postValue(recipesLiveData.value?.plus(recipes))
            }
        }
    }

    fun loadMore() {
        if (launchedLoadMoreJob == null && lastLoadMoreCounts != 0) {
            launchedLoadMoreJob = viewModelScope.launch(Dispatchers.IO) {
                val recipes = getRecipesByTypeUseCase(mealType, 30, size)
                lastLoadMoreCounts = recipes.size
                size += lastLoadMoreCounts
                println(size)
                withContext(Dispatchers.Main) {
                    recipesLiveData.postValue(recipesLiveData.value?.plus(recipes))
                    launchedLoadMoreJob = null
                }
            }
        }
    }
}