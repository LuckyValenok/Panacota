package net.panacota.app.domain.data

import net.panacota.app.R

enum class MealType(private val stringResource: Int) {
    MAIN_COURSE(R.string.main_course),
    SIDE_DISH(R.string.side_dish),
    DESSERT(R.string.dessert),
    APPETIZER(R.string.appetizer),
    SALAD(R.string.salad),
    BREAD(R.string.bread),
    BREAKFAST(R.string.breakfast),
    SOUP(R.string.soup),
    BEVERAGE(R.string.beverage),
    SAUCE(R.string.sauce),
    MARINADE(R.string.marinade),
    FINGERFOOD(R.string.fingerfood),
    SNACK(R.string.snack),
    DRINK(R.string.drink);

    override fun toString(): String = name.lowercase().replace('_', ' ')

    fun getStringResource(): Int = stringResource

    companion object {
        val ARRAY = values()
    }
}