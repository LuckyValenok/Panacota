package net.panacota.app.domain.data

import android.content.res.Resources
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import net.panacota.app.R

@Entity
data class Recipe(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction>?,
    @SerializedName("dishTypes")
    val dishTypes: List<String>
) {
    fun toSpannableStringBuilder(resources: Resources): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
        addTitleWithContent(builder, resources.getString(R.string.summary), summary)
        if (extendedIngredients.isNotEmpty()) {
            var i = 1
            val ingredients = extendedIngredients.joinToString("<br>") { "${i++}. ${it.original}" }
            addTitleWithContent(builder, resources.getString(R.string.ingredients), ingredients)
        }
        val titleInstruction = resources.getString(R.string.instruction)
        if (!analyzedInstructions.isNullOrEmpty()) {
            val instruction =
                analyzedInstructions.joinToString("<br>") {
                    val name = if (it.name.isNotEmpty()) "${it.name}: <br>" else ""
                    name + it.steps.joinToString("<br>") { step -> "${step.number}. ${step.step}" }
                }
            addTitleWithContent(builder, resources.getString(R.string.instruction), instruction)
        } else {
            addTitleWithContent(builder, titleInstruction, "<a href=\"${sourceUrl}\">${sourceName}</a>")
        }
        return builder
    }

    private fun addTitleWithContent(builder: SpannableStringBuilder, title: String, content: String) {
        builder.append(title)
        builder.append("\n")
        builder.setSpan(
            AbsoluteSizeSpan(19, true),
            builder.length - title.length - 1,
            builder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append("\n")
        val htmlContent = Html.fromHtml(content)
        builder.append(htmlContent)
        builder.setSpan(
            AbsoluteSizeSpan(15, true),
            builder.length - htmlContent.length,
            builder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append("\n\n")
    }
}
