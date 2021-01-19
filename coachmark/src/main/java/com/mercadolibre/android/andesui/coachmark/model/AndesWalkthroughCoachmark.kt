package com.mercadolibre.android.andesui.coachmark.model
import androidx.core.widget.NestedScrollView
import android.view.View
import java.io.Serializable

data class AndesScrollessWalkthroughCoachmark(
        val steps: MutableList<AndesWalkthroughCoachmarkStep>,
        val view: View,
        val completionHandler: () -> Unit
) : Serializable

data class AndesWalkthroughCoachmark(
    val steps: MutableList<AndesWalkthroughCoachmarkStep>,
    val scrollView: NestedScrollView,
    val completionHandler: () -> Unit
) : Serializable

data class AndesWalkthroughCoachmarkStep(
    val title: String,
    val description: String,
    val nextText: String,
    var view: View,
    val style: AndesWalkthroughCoachmarkStyle
) : Serializable

enum class AndesWalkthroughCoachmarkStyle {
    CIRCLE,
    RECTANGLE,
    HAMBURGER,
    MENU_ITEM
}
