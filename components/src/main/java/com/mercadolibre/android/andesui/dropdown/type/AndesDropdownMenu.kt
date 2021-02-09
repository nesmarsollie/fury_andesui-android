package com.mercadolibre.android.andesui.dropdown.type

internal interface AndesDropdownMenu {
    fun getType(): AndesDropdownMenuType
}

internal object AndesDropdownMenuTypeBottomSheet : AndesDropdownMenu {
    override fun getType() = AndesDropdownMenuType.BOTTOMSHEET
}

internal object AndesDropdownMenuTypeFloatingMenu : AndesDropdownMenu {
    override fun getType() = AndesDropdownMenuType.FLOATINGMENU
}

