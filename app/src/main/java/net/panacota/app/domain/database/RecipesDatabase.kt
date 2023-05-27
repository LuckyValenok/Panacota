package net.panacota.app.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.panacota.app.domain.database.entity.FavoriteRecipe
import net.panacota.app.domain.database.entity.Recipes

@Database(entities = [Recipes::class, FavoriteRecipe::class], version = 1)
@TypeConverters(RecipesConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}