package com.mercadolibre.android.andesui.dropdown.size

/**
 * This class handle the Dropdown size, based on {SMALL, MEDIUM, LARGE} values
 */
enum class AndesDropdownSize {
    SMALL,
    MEDIUM,
    LARGE;

    companion object {
        fun fromString(value: String): AndesDropdownSize = valueOf(value.toUpperCase())
    }

    internal val size get() = getDropDownSize()

    private fun getDropDownSize(): AndesDropdownSizeInterface {
        return when (this) {
            SMALL -> AndesDropdownSmallSize()
            MEDIUM -> AndesDropdownMediumSize()
            LARGE -> AndesDropdownLargeSize()
        }
    }
}