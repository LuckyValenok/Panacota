package net.panacota.app.domain.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.panacota.app.domain.data.Recipe

@Entity
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    var recipe: Recipe
)