package net.panacota.app.domain.repository

import androidx.annotation.IntRange
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import retrofit2.Response

interface Repository {
    suspend fun getRecipesByFilters(
            filters: Map<String, String>,
            @IntRange(1, 100) limit: Int,
            @IntRange(0, 900) offset: Int
    ): Response<ListRecipe>
}