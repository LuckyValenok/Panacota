package net.panacota.app.domain.usecases.getRecipesByFilters

import androidx.annotation.IntRange
import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.Recipe
import net.panacota.app.domain.database.RecipesDao
import net.panacota.app.domain.repository.Repository
import java.net.UnknownHostException
import javax.inject.Inject

class GetRecipesByFiltersUseCaseImpl @Inject constructor(
        private val repository: Repository,
        private val recipesDao: RecipesDao
) : GetRecipesByFiltersUseCase {
    override suspend fun invoke(
        filters: Map<String, String>,
        @IntRange(1, 100) limit: Int,
        offset: Int
    ): List<Recipe> {
        try {
            val response = repository.getRecipesByFilters(filters, limit, offset)
            if (response.isSuccessful) {
                val recipes: ListRecipe = response.body()!!
                return recipes.results
            }
        } catch (e: UnknownHostException) {
            return if (filters.containsKey("query")) {
                recipesDao.search(filters["query"]!!, limit, offset)
            } else {
                recipesDao.getRecipesByFilters(
                    filters["type"],
                    filters["diet"],
                    filters["cuisine"],
                    limit,
                    offset
                )
            }
        }
        return emptyList()
    }
}