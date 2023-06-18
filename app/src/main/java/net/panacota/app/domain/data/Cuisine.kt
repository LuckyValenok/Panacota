package net.panacota.app.domain.data

import net.panacota.app.R

enum class Cuisine(private val stringResource: Int) : IResourced {
    AFRICAN(R.string.african),
    AMERICAN(R.string.american),
    BRITISH(R.string.british),
    CAJUN(R.string.cajun),
    CARIBBEAN(R.string.caribbean),
    CHINESE(R.string.chinese),
    EASTERN_EUROPEAN(R.string.eastern_european),
    EUROPEAN(R.string.european),
    FRENCH(R.string.french),
    GERMAN(R.string.german),
    GREEK(R.string.greek),
    INDIAN(R.string.indian),
    IRISH(R.string.irish),
    ITALIAN(R.string.italian),
    JAPANESE(R.string.japanese),
    JEWISH(R.string.jewish),
    KOREAN(R.string.korean),
    LATIN_AMERICAN(R.string.latin_american),
    MEDITERRANEAN(R.string.mediterranean),
    MEXICAN(R.string.mexican),
    MIDDLE_EASTERN(R.string.middle_eastern),
    NORDIC(R.string.nordic),
    SOUTHERN(R.string.southern),
    SPANISH(R.string.spanish),
    THAI(R.string.thai),
    VIETNAMESE(R.string.vietnamese);

    override fun toString(): String = name.lowercase().replace('_', ' ')

    override fun getStringResource(): Int = stringResource

    companion object {
        val ARRAY = Cuisine.values()
    }
}