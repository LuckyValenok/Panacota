package net.panacota.app.domain.usecases.getRecipes

import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.repository.Repository
import retrofit2.Response
import javax.inject.Inject

class GetRecipesUseCaseImpl @Inject constructor(private val repository: Repository) :
    GetRecipesUseCase {
    override suspend fun invoke(): Response<ListRecipe> = repository.getRecipes()
}