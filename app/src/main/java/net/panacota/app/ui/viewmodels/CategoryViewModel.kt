package net.panacota.app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.usecases.getRecipesByType.GetRecipesByTypeUseCase
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val getRecipesByTypeUseCase: GetRecipesByTypeUseCase) :
    ViewModel() {
    val recipes = MutableLiveData<List<Recipe>>()

    fun start(mealType: MealType) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getRecipesByTypeUseCase(mealType)
            if (response.isSuccessful) {
                val recipes: ListRecipe = response.body()!!
                withContext(Dispatchers.Main) {
                    this@CategoryViewModel.recipes.postValue(recipes.results)
                }
            }
        }
    }
}