package net.panacota.app.domain.data

import com.google.gson.annotations.SerializedName

data class AnalyzedInstruction(
    @SerializedName("name")
    val name: String,
    @SerializedName("steps")
    val steps: List<Step>
)

data class Step(
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
)