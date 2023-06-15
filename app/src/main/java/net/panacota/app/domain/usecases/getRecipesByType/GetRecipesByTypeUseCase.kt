package net.panacota.app.domain.usecases.getRecipesByType

import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import retrofit2.Response

interface GetRecipesByTypeUseCase {
    suspend operator fun invoke(mealType: MealType): Response<ListRecipe>
}