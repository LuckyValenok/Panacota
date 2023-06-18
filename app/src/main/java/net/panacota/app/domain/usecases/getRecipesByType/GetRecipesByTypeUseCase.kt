package net.panacota.app.domain.usecases.getRecipesByType

import androidx.annotation.IntRange
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe
import retrofit2.Response

interface GetRecipesByTypeUseCase {
    suspend operator fun invoke(mealType: MealType, @IntRange(1, 100) limit: Int = 10): List<Recipe>
}