package com.mercadolibre.android.andesui.dropdown.utils

import com.mercadolibre.android.andesui.list.utils.AndesListDelegate

interface AndesDropdownDelegate {

    fun onItemSelected(andesDropDown: AndesListDelegate, position: Int)

}
