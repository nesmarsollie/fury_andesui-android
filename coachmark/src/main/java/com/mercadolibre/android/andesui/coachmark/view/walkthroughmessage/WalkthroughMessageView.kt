package com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage

import android.content.Context
import android.graphics.Rect
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessagePosition
import com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless.WalkthroughScrollessMessageView
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import kotlinx.android.synthetic.main.andes_walkthrough_message.view.*
import kotlinx.android.synthetic.main.andes_walkthrough_message.view.arcArrowBottom
import kotlinx.android.synthetic.main.andes_walkthrough_message.view.arcArrowTop
import kotlinx.android.synthetic.main.andes_walkthrough_message.view.walkthroughDescription
import kotlinx.android.synthetic.main.andes_walkthrough_message.view.walkthroughTitle

class WalkthroughMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var position: WalkthroughMessagePosition = WalkthroughMessagePosition.BELOW
    private var walkthroughButtonClicklistener: WalkthroughScrollessMessageView.WalkthroughButtonClicklistener? = null

    init {
        inflate(context, R.layout.andes_walkthrough_message, this)
        walkthroughTitle.typeface = context.getFontOrDefault(R.font.andes_font_semibold)
        walkthroughDescription.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        walkthroughNextButton.typeface = context.getFontOrDefault(R.font.andes_font_semibold)
    }

    fun setListener(walkthroughButtonClicklistener: WalkthroughScrollessMessageView.WalkthroughButtonClicklistener) {
        this.walkthroughButtonClicklistener = walkthroughButtonClicklistener
    }

    fun setPosition(position: Int) {
        walkthroughNextButton.setOnClickListener { walkthroughButtonClicklistener?.onClickNextButton(position) }
    }

    fun setData(data: WalkthroughMessageModel, lastPosition: Boolean) {
        if (lastPosition) {
            walkthroughNextButton.setBackgroundResource(R.drawable.andes_walkthrough_configuration_blue_button_background)
        } else {
            walkthroughNextButton.setBackgroundResource(R.drawable.andes_walkthrough_configuration_button_background)
        }

        walkthroughTitle.text = data.title
        walkthroughDescription.text = data.description
        walkthroughNextButton.text = data.buttonText

        checkViewVisibility(walkthroughTitle, data.title)
        checkViewVisibility(walkthroughDescription, data.description)
        checkViewVisibility(walkthroughNextButton, data.buttonText)
    }

    private fun checkViewVisibility(view: View, content: String) {
        view.visibility = if (content.isNotBlank()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun definePosition(overlayRect: Rect, targetRect: Rect) {
        setPosition(overlayRect, targetRect)
        setArrow(targetRect)
    }

    private fun setPosition(overlayRect: Rect, targetRect: Rect) {
        val centerReferenceView = (targetRect.bottom + targetRect.top) / 2
        val centerScreen = (overlayRect.bottom + overlayRect.top) / 2
        position = if (centerReferenceView <= centerScreen) {
            WalkthroughMessagePosition.BELOW
        } else {
            WalkthroughMessagePosition.ABOVE
        }
    }

    private fun setArrow(targetRect: Rect) {
        val tooltipRect = Rect()
        val centerTarget = (targetRect.left + targetRect.right) / 2
        val centerTooltip: Int

        val paddingTop = context.resources.getDimension(R.dimen.andes_coachmark_default_padding).toInt()

        when (position) {
            WalkthroughMessagePosition.BELOW -> {

                arcArrowTop.visibility = View.VISIBLE
                arcArrowBottom.visibility = View.GONE
                arcArrowTop.getGlobalVisibleRect(tooltipRect)
                centerTooltip = (tooltipRect.left + tooltipRect.right) / 2
                if (isNecessaryShowArrow(centerTooltip, centerTarget)) {
                    arcArrowTop.addRect(centerTooltip, tooltipRect.bottom - paddingTop, centerTarget, tooltipRect.top - paddingTop)
                } else {
                    arcArrowTop.visibility = View.GONE
                }
            }
            WalkthroughMessagePosition.ABOVE -> {

                arcArrowTop.visibility = View.GONE
                arcArrowBottom.visibility = View.VISIBLE
                arcArrowBottom.getGlobalVisibleRect(tooltipRect)
                centerTooltip = (tooltipRect.left + tooltipRect.right) / 2
                if (isNecessaryShowArrow(centerTooltip, centerTarget)) {
                    arcArrowBottom.addRect(centerTooltip, tooltipRect.top - paddingTop, centerTarget, tooltipRect.bottom - paddingTop)
                } else {
                    arcArrowBottom.visibility = View.GONE
                }
            }
        }
    }

    @SuppressWarnings("ReturnCount")
    private fun isNecessaryShowArrow(centerTooltip: Int, centerTarget: Int): Boolean {
        val minWithForShowArrow = context.resources.getDimension(R.dimen.andes_coachmark_min_with_for_show_arrow)
        if (centerTooltip - centerTarget >= 0 && centerTooltip - centerTarget <= minWithForShowArrow) {
            return false
        } else if (centerTarget - centerTooltip >= 0 && centerTarget - centerTooltip <= minWithForShowArrow) {
            return false
        }
        return true
    }

    fun clear() {
        arcArrowBottom.clear()
        arcArrowTop.clear()
        arcArrowBottom.visibility = View.VISIBLE
        arcArrowTop.visibility = View.GONE
        walkthroughTitle.visibility = View.GONE
        walkthroughDescription.visibility = View.GONE
    }

    fun getPosition(): WalkthroughMessagePosition {
        return position
    }

    interface WalkthroughButtonClicklistener {
        fun onClickNextButton(position: Int)
    }
}
