package net.panacota.app.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.panacota.app.domain.data.Diet
import net.panacota.app.domain.data.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipe WHERE " +
            "(:mealType IS NULL OR LOWER(dishTypes) LIKE '%' || LOWER(:mealType) || '%') AND " +
            "(:diet IS NULL OR LOWER(diets) LIKE '%' || LOWER(:diet) || '%') AND " +
            "(:cuisine IS NULL OR LOWER(cuisines) LIKE '%' || LOWER(:cuisine) || '%') " +
            "LIMIT CASE WHEN :limit <= 0 THEN -1 ELSE :limit END OFFSET :offset")
    suspend fun getRecipesByFilters(
            mealType: String?,
            diet: String?,
            cuisine: String?,
            limit: Int = -1,
            offset: Int = 0
    ): List<Recipe>

    @Query("SELECT * FROM recipe WHERE LOWER(title) LIKE LOWER(:str) || '%' " +
            "LIMIT CASE WHEN :limit <= 0 THEN -1 ELSE :limit END OFFSET :offset")
    suspend fun search(
        str: String,
        limit: Int = -1,
        offset: Int = 0
    ): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)
}