package com.mercadolibre.android.andesui.dropdown.size

import android.content.Context
import com.mercadolibre.android.andesui.R

/**
 * Defines all size related properties that an [AndesDropdown] needs to be drawn properly.
 * Those properties change depending on the size of the dropdown.
 */
internal interface AndesDropdownSizeInterface {
    fun titleFontSize(context: Context): Float
}

internal class AndesDropdownSmallSize : AndesDropdownSizeInterface {
    override fun titleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_dropdown_title_font_size_small)
}

internal class AndesDropdownMediumSize : AndesDropdownSizeInterface {
    override fun titleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_dropdown_title_font_size_medium)
}

internal class AndesDropdownLargeSize : AndesDropdownSizeInterface {
    override fun titleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_dropdown_title_font_size_large)
}