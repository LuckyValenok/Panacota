package net.panacota.app.domain.data

import com.google.gson.annotations.SerializedName

data class ListRecipe(
    @SerializedName("results")
    val results: List<Recipe>
)