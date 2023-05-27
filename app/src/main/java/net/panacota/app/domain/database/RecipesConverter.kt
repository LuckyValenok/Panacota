package net.panacota.app.domain.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.panacota.app.domain.data.Recipe

class RecipesConverter {
    private val gson = Gson()

    @TypeConverter
    fun listRecipesToString(list: ListRecipe): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToListRecipes(str: String): List<Recipe> {
        return gson.fromJson(str, ListRecipe::class.java).list
    }

    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(str: String): Recipe {
        return gson.fromJson(str, Recipe::class.java)
    }
}

data class ListRecipe(val list: List<Recipe>)