package com.mercadolibre.android.andesui.coachmark.model

import java.io.Serializable

data class WalkthroughMessageModel(
    val title: String,
    val description: String,
    val buttonText: String = ""
): Serializable

enum class WalkthroughMessagePosition {
    ABOVE,
    BELOW
}
