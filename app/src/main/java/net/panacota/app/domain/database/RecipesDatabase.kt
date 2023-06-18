package net.panacota.app.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.panacota.app.domain.data.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(RecipesConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        const val NAME = "RecipesDatabase"
    }
}