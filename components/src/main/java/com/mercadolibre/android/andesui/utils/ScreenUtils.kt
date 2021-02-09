package com.mercadolibre.android.andesui.utils

import android.content.res.Resources

internal object ScreenUtils {
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
