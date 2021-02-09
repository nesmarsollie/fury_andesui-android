package com.mercadolibre.android.andesui.dropdown.factory

import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType

internal data class AndesDropdownConfiguration(
        val menuType: AndesDropdownMenuType,
        val label: String?,
        val helper: String?,
        val placeHolder: String?
)

internal object AndesDropdownConfigurationFactory {

    fun create(andesDropdownAttrs: AndesDropdownAttrs): AndesDropdownConfiguration {
        return with(andesDropdownAttrs) {
            AndesDropdownConfiguration(
                    menuType = andesDropdownAttrs.andesDropdownMenuType,
                    label = andesDropdownAttrs.andesDropdownLabel,
                    helper = andesDropdownAttrs.andesDropdownHelper,
                    placeHolder = andesDropdownAttrs.andesDropdownPlaceHolder
            )
        }
    }

}
