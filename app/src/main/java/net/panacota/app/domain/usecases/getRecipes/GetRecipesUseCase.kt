package net.panacota.app.domain.usecases.getRecipes

import net.panacota.app.domain.data.ListRecipe
import retrofit2.Response

interface GetRecipesUseCase {
    suspend operator fun invoke(): Response<ListRecipe>
}