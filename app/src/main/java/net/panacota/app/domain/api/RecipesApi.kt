package net.panacota.app.domain.api

import net.panacota.app.domain.data.ListRecipe
import net.panacota.app.domain.data.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipesApi {
    /**
     * @see <a href="https://spoonacular.com/food-api/docs#Search-Recipes-Complex">Search Recipes Complex</a>
     */
    @GET("complexSearch")
    suspend fun complexSearch(
        @QueryMap query: Map<String, String>? = null
    ): Response<ListRecipe>

    companion object {
        val BASE_URL = "https://api.spoonacular.com/recipes/"
    }
}