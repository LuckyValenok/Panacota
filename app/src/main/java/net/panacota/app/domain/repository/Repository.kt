package net.panacota.app.domain.repository

import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import retrofit2.Response

interface Repository {
    suspend fun getRecipes(): Response<ListRecipe>

    suspend fun getRecipesByType(mealType: MealType): Response<ListRecipe>
}