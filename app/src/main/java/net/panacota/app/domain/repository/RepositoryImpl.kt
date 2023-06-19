package net.panacota.app.domain.repository

import androidx.annotation.IntRange
import net.panacota.app.domain.api.RecipesApi
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.database.RecipesDao
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val recipesApi: RecipesApi,
    private val recipesDao: RecipesDao
) : Repository {
    override suspend fun getRecipesByFilters(
        filters: Map<String, String>,
        @IntRange(1, 100) limit: Int,
        offset: Int
    ): Response<ListRecipe> {
        val response = recipesApi.complexSearch(
            filters.plus(
                mapOf(
                    "number" to limit.toString(),
                    "offset" to offset.toString(),
                    "addRecipeInformation" to "true",
                    "fillIngredients" to "true"
                )
            )
        )
        if (response.isSuccessful) {
            val recipes: ListRecipe = response.body()!!
            recipesDao.insertRecipes(recipes.results)
        }
        return response
    }
}