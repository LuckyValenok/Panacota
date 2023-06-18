package net.panacota.app.domain.usecases.getRecipesByFilters

import androidx.annotation.IntRange
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.database.RecipesDao
import net.panacota.app.domain.repository.Repository
import java.net.UnknownHostException
import javax.inject.Inject

class GetRecipesByFiltersUseCaseImpl @Inject constructor(
        private val repository: Repository,
        private val recipesDao: RecipesDao
) : GetRecipesByFiltersUseCase {
    override suspend fun invoke(
            filters: Map<String, String>,
            @IntRange(1, 100) limit: Int,
            @IntRange(0, 900) offset: Int
    ): List<Recipe> {
        try {
            val response = repository.getRecipesByFilters(filters, limit, offset)
            if (response.isSuccessful) {
                val recipes: ListRecipe = response.body()!!
                recipesDao.insertRecipes(recipes.results)
                return recipes.results
            }
        } catch (e: UnknownHostException) {
            if (filters.containsKey("query")) {
                return recipesDao.search(filters["query"]!!, limit, offset)
            } else {
                return recipesDao.getAllByDishType(filters["mealType"]!!, limit, offset)
            }
        }
        return emptyList()
    }
}