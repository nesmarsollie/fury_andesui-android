package com.mercadolibre.android.andesui.coachmark.presenter

import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep

@SuppressWarnings("TooManyFunctions")
internal interface CoachmarkViewInterface {

    fun cleanCoachmarkOverlayView()

    fun clearWalkthroughMessageView()

    fun setWalkthroughMessageViewY(positionY: Float)

    fun setScrollViewPaddings(left: Int, top: Int, right: Int, bottom: Int)

    fun getFooterHeigh(): Int

    fun getToolbarSize(): Int

    fun getTooltipMargin(): Int

    fun getScrollViewPaddingFromDimen(): Int

    fun addTarget(stepReferenced: AndesWalkthroughCoachmarkStep)

    fun scrollTo(scrollToY: Int)

    fun animateScroll(isVisible: Boolean, scrollToY: Int, stepReferenced: AndesWalkthroughCoachmarkStep)

    fun addRoundRect(stepReferenced: AndesWalkthroughCoachmarkStep)

    fun addCircleRect(stepReferenced: AndesWalkthroughCoachmarkStep)
}
