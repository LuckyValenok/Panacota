package net.panacota.app.domain.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.panacota.app.domain.data.AnalyzedInstruction
import net.panacota.app.domain.data.ExtendedIngredient
import java.lang.reflect.Type

class RecipesConverter {
    private val gson = Gson()

    @TypeConverter
    fun extendedIngredientsToString(list: List<ExtendedIngredient>): String {
        val type: Type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToExtendedIngredients(str: String): List<ExtendedIngredient> {
        val type: Type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun analyzedInstructionsToString(analyzedInstructions: List<AnalyzedInstruction>): String {
        val type: Type = object : TypeToken<List<AnalyzedInstruction>>() {}.type
        return gson.toJson(analyzedInstructions, type)
    }

    @TypeConverter
    fun stringToAnalyzedInstructions(str: String): List<AnalyzedInstruction> {
        val type: Type = object : TypeToken<List<AnalyzedInstruction>>() {}.type
        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun stringsToString(strings: List<String>): String {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(strings, type)
    }

    @TypeConverter
    fun stringToStrings(str: String): List<String> {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(str, type)
    }
}