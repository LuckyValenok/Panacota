package net.panacota.app.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.panacota.app.domain.data.MealType
import net.panacota.app.domain.data.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipe WHERE dishTypes LIKE '%' || :mealType || '%' " +
            "LIMIT CASE WHEN :limit <= 0 THEN -1 ELSE :limit END OFFSET :offset")
    suspend fun getAllByDishType(
            mealType: String,
            limit: Int = -1,
            offset: Int = 0
    ): List<Recipe>

    @Query("SELECT * FROM recipe WHERE LOWER(title) LIKE :str " +
            "LIMIT CASE WHEN :limit <= 0 THEN -1 ELSE :limit END OFFSET :offset")
    suspend fun search(
        str: String,
        limit: Int = -1,
        offset: Int = 0
    ): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)
}