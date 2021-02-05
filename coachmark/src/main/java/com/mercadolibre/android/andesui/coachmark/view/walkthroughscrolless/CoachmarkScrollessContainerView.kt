package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.view.CoachmarkOverlay
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import kotlinx.android.synthetic.main.andes_walkthrough_scrolless_container.view.*

class CoachmarkScrollessContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var coachMarkContainerListener: CoachmarkContainerListener? = null

    init {
        inflate(context, R.layout.andes_walkthrough_scrolless_container, this)
        counterText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
    }

    fun setListener(coachMarkContainerListener: CoachmarkContainerListener) {
        this.coachMarkContainerListener = coachMarkContainerListener
    }

    fun setData(position: Int, size: Int) {
        closeButton.setOnClickListener { coachMarkContainerListener?.onClickClose(position) }
        counterText.text = context.resources.getString(R.string.andes_coachmark_header_numeration_of, position + 1, size)
    }

    fun getHamburgerView(): View {
        return hamburguerView
    }

    internal fun getHeaderView(): CoachmarkOverlay {
        return headerBackground
    }

    fun getCloseButtonView(): View {
        return closeButton
    }

    interface CoachmarkContainerListener {
        fun onClickClose(position: Int)
    }
}
