package net.panacota.app.domain.usecases.getRecipesByType

import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.repository.Repository
import retrofit2.Response
import javax.inject.Inject

class GetRecipesByTypeUseCaseImpl @Inject constructor(private val repository: Repository) :
    GetRecipesByTypeUseCase {
    override suspend fun invoke(mealType: MealType): Response<ListRecipe> =
        repository.getRecipesByType(mealType)
}