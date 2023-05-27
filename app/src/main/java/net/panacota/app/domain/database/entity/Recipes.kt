package net.panacota.app.domain.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.panacota.app.domain.database.ListRecipe

@Entity
data class Recipes(val recipes: ListRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}