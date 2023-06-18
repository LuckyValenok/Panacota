package net.panacota.app.domain.data

import net.panacota.app.R

enum class Diet(private val stringResource: Int) : IResourced {
    GLUTEN_FREE(R.string.gluten_free),
    KETOGENIC(R.string.ketogenic),
    VEGETARIAN(R.string.vegetarian),
    LACTO_VEGETARIAN(R.string.lacto_vegetarian),
    OVO_VEGETARIAN(R.string.ovo_vegetarian),
    VEGAN(R.string.vegan),
    PESCETARIAN(R.string.pescetarian),
    PALEO(R.string.paleo),
    PRIMAL(R.string.primal),
    LOW_FODMAP(R.string.low_fodmap),
    WHOLE30(R.string.whole30);

    override fun toString(): String = name.lowercase().replace('_', ' ')

    override fun getStringResource(): Int = stringResource

    companion object {
        val ARRAY = Diet.values()
    }
}