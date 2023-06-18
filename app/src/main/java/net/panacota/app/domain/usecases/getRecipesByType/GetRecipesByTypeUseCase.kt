package net.panacota.app.domain.usecases.getRecipesByType

import androidx.annotation.IntRange
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe

interface GetRecipesByTypeUseCase {
    suspend operator fun invoke(
            mealType: MealType,
            @IntRange(1, 100) limit: Int = 10,
            offset: Int = 0
    ): List<Recipe>
}