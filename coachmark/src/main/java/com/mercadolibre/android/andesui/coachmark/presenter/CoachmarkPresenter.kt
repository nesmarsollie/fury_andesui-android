package com.mercadolibre.android.andesui.coachmark.presenter

import android.graphics.Rect
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessagePosition

internal class CoachmarkPresenter(private val view: CoachmarkViewInterface) {

    /**
     * Verifica si la vista a resaltar esta visible o no, para realzar el scroll
     *
     * @param stepReferenced es el step a resaltar
     * @param heightScreen es el alto de la pantalla
     * @param stepReferenceHitRect informacion sobre la vista a resaltar
     * @param stepReferenceGlobalRect informacion sobre la vista a resaltar
     * @param bodyGlobalRect informacion el overlay
     * @param tooltipHeight el alto del tooltip
     * @param tooltipPosition la posicion del tooltip
     */
    @Suppress("LongParameterList")
    fun resolveScrollMode(
            stepReferenced: AndesWalkthroughCoachmarkStep,
            heightScreen: Int,
            stepReferencedHeight: Int,
            stepReferenceGlobalRect: Rect,
            bodyGlobalRect: Rect,
            tooltipHeight: Int,
            tooltipPosition: WalkthroughMessagePosition
    ) {

        stepReferenced.view?.let {
            val isBodyMoreBottom = bodyGlobalRect.bottom < stepReferenceGlobalRect.bottom
            if (isBodyMoreBottom || !it.getLocalVisibleRect(bodyGlobalRect) || bodyGlobalRect.height() < it.height) {
                resolvePartialOrNotViewedReferenceView(
                        stepReferenced,
                        heightScreen,
                        stepReferencedHeight,
                        stepReferenceGlobalRect,
                        tooltipHeight,
                        tooltipPosition
                )
            } else {
                resolveCompleteReferenceView(
                        stepReferenced,
                        heightScreen,
                        stepReferencedHeight,
                        stepReferenceGlobalRect,
                        tooltipHeight,
                        tooltipPosition
                )
            }
        }
    }

    /**
     * Casos en el que el stepReference no este visible o este parcialmente visible
     */
    private fun resolvePartialOrNotViewedReferenceView(
            stepReferenced: AndesWalkthroughCoachmarkStep,
            heightScreen: Int,
            stepReferencedHeight: Int,
            targetRect: Rect,
            tooltipHeight: Int,
            tooltipPosition: WalkthroughMessagePosition
    ) {

        var scrollToY = 0
        var paddingScrollView = view.getScrollViewPaddingFromDimen()
        val toolbarSize = view.getToolbarSize()
        val tooltipPadding = view.getTooltipMargin()

        when {
            tooltipPosition == WalkthroughMessagePosition.ABOVE -> {

                scrollToY = targetRect.top -
                        toolbarSize -
                        tooltipHeight -
                        tooltipPadding -
                        paddingScrollView
                paddingScrollView += view.getFooterHeigh()
                view.setScrollViewPaddings(0, 0, 0, paddingScrollView)
            }
            tooltipPosition == WalkthroughMessagePosition.BELOW -> {

                scrollToY = targetRect.top +
                        stepReferencedHeight -
                        toolbarSize -
                        heightScreen +
                        tooltipHeight +
                        view.getFooterHeigh()
            }
        }
        view.animateScroll(false, scrollToY, stepReferenced)
    }

    /**
     * Casos en el que esta visible el elemento
     */
    private fun resolveCompleteReferenceView(
            stepReferenced: AndesWalkthroughCoachmarkStep,
            heightScreen: Int,
            stepReferencedHeight: Int,
            targetRect: Rect,
            tooltipHeight: Int,
            tooltipPosition: WalkthroughMessagePosition
    ) {

        val toolbarSize = view.getToolbarSize()
        var scrollToY = targetRect.top - toolbarSize
        var paddingScrollView = view.getScrollViewPaddingFromDimen()
        val spaceBottomReferenceView = heightScreen + toolbarSize - targetRect.top + stepReferencedHeight

        when {
            // Cuando el tooltip no tiene lugar para ser posicionado arriba
            tooltipPosition == WalkthroughMessagePosition.ABOVE && targetRect.top - toolbarSize < tooltipHeight -> {
                val spaceTopReferenceView = targetRect.top - toolbarSize
                scrollToY -= tooltipHeight
                paddingScrollView += tooltipHeight - spaceTopReferenceView
                view.setScrollViewPaddings(0, paddingScrollView, 0, 0)
                view.animateScroll(false, scrollToY, stepReferenced)
                return

            }
            // Cuando el tooltip no tiene lugar para ser posicionado debajo
            tooltipPosition == WalkthroughMessagePosition.BELOW && spaceBottomReferenceView < tooltipHeight -> {
                scrollToY += tooltipHeight - spaceBottomReferenceView
                view.setScrollViewPaddings(0, 0, 0, view.getFooterHeigh())
                view.animateScroll(false, scrollToY, stepReferenced)
                return
            }
        }
        view.animateScroll(true, scrollToY, stepReferenced)
    }

    fun addRect(stepReferenced: AndesWalkthroughCoachmarkStep) {
        when (stepReferenced.style) {
            AndesWalkthroughCoachmarkStyle.CIRCLE -> {
                view.addCircleRect(stepReferenced)
            }
            AndesWalkthroughCoachmarkStyle.RECTANGLE -> {
                view.addRoundRect(stepReferenced)
            }
            AndesWalkthroughCoachmarkStyle.MENU_ITEM -> {
                view.addRoundRect(stepReferenced)
            }
            else -> {
                view.addRoundRect(stepReferenced)
            }
        }
    }

    /**
     * Coloca al tooltip en la posicion de la vista a referenciar (arriba o debajo)
     */
    fun relocateTooltip(
            tooltipHeight: Int,
            tooltipPosition: WalkthroughMessagePosition,
            targetRect: Rect
    ) {

        val padding = view.getTooltipMargin().toFloat()
        val height = targetRect.height()
        val y = targetRect.top

        // setY
        if (tooltipPosition == WalkthroughMessagePosition.BELOW) {
            view.setWalkthroughMessageViewY((y + height + padding))
        } else {
            view.setWalkthroughMessageViewY((y - tooltipHeight - padding))
        }
    }

    /**
     * Luego de cambiar de vista a referenciar se limpian los valores
     */
    fun restorePreviousValues() {

        view.cleanCoachmarkOverlayView()
        view.clearWalkthroughMessageView()
        view.setWalkthroughMessageViewY(0F)
        view.setScrollViewPaddings(0, 0, 0, 0)
    }
}
