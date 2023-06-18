package net.panacota.app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.usecases.getRecipesByType.GetRecipesByTypeUseCase
import net.panacota.app.ui.adapters.Category
import javax.inject.Inject

class MainViewModel @Inject constructor(getRecipesByTypeUseCase: GetRecipesByTypeUseCase) :
    ViewModel() {
    val categories = MutableLiveData<List<Category>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<Category>()
            for (value in MealType.values()) {
                val recipes = getRecipesByTypeUseCase(value)
                list += Category(value, recipes)
                withContext(Dispatchers.Main) {
                    this@MainViewModel.categories.postValue(list)
                }
            }
        }
    }
}