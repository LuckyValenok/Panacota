package net.panacota.app.domain.data

import net.panacota.app.R

enum class Intolerance(private val stringResource: Int) : IResourced {
    DAIRY(R.string.dairy),
    EGG(R.string.egg),
    GLUTEN(R.string.gluten),
    GRAIN(R.string.grain),
    PEANUT(R.string.peanut),
    SEAFOOD(R.string.seafood),
    SESAME(R.string.sesame),
    SHELLFISH(R.string.shellfish),
    SOY(R.string.soy),
    SULFITE(R.string.sulfite),
    TREE_NUT(R.string.tree_nut),
    WHEAT(R.string.wheat);

    override fun toString(): String = name.lowercase().replace('_', ' ')

    override fun getStringResource(): Int = stringResource

    companion object {
        val ARRAY = Intolerance.values()
    }
}