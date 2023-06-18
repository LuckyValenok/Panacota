package net.panacota.app.domain.usecases.getRecipesByType

import androidx.annotation.IntRange
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.database.RecipesDao
import net.panacota.app.domain.repository.Repository
import java.net.UnknownHostException
import javax.inject.Inject

class GetRecipesByTypeUseCaseImpl @Inject constructor(
        private val repository: Repository,
        private val recipesDao: RecipesDao
) : GetRecipesByTypeUseCase {
    override suspend fun invoke(
            mealType: MealType,
            @IntRange(1, 100) limit: Int,
            @IntRange(0, 900) offset: Int
    ): List<Recipe> {
        try {
            val response = repository.getRecipesByType(mealType, limit, offset)
            if (response.isSuccessful) {
                val recipes: ListRecipe = response.body()!!
                recipesDao.insertRecipes(recipes.results)
                return recipes.results
            }
        } catch (e: UnknownHostException) {
            return recipesDao.getAllByDishType(mealType, limit, offset)
        }
        return emptyList()
    }
}