package net.panacota.app.domain.usecases.getRecipesByFilters

import androidx.annotation.IntRange
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe

interface GetRecipesByFiltersUseCase {
    suspend operator fun invoke(
            filters: Map<String, String>,
            @IntRange(1, 100) limit: Int = 10,
            offset: Int = 0
    ): List<Recipe>
}