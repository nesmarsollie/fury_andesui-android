package com.mercadolibre.android.andesui.coachmark.view

import android.content.Context
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.mercadolibre.android.andesui.coachmark.R

internal class CoachmarkOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintFill: Paint = Paint()
    private val paintStroke: Paint = Paint()
    var rectF = mutableListOf<RectF>()
    var radius: Float
    private var padding: Float
    var isCircle: Boolean = false
    private var x: Int = 0
    private var y: Int = 0

    init {
        isDrawingCacheEnabled = true
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paintFill.isAntiAlias = true
        paintFill.color = ContextCompat.getColor(context, R.color.andes_transparent)
        paintFill.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paintStroke.strokeWidth = context.resources.getDimension(R.dimen.andes_coachmark_padding_border_overlay)
        paintStroke.style = Paint.Style.STROKE
        paintStroke.color = ContextCompat.getColor(context, R.color.andes_accent_color)

        padding = context.resources.getDimension(R.dimen.andes_coachmark_padding_internal_overlay)
        radius = context.resources.getDimension(R.dimen.andes_coachmark_default_radius_overlay)
    }

    @Suppress("LongParameterList")
    fun addRect(x: Int, y: Int, width: Int, height: Int, isCircle: Boolean, radius: Float? = null,
                showPadding: Boolean = true) {

        this.radius = radius ?: context.resources.getDimension(R.dimen.andes_coachmark_default_radius_overlay)
        this.isCircle = isCircle
        this.x = x
        this.y = y

        val r = x + width
        val b = y + height

        if (showPadding) {
            rectF.add(RectF(
                    (x - padding),
                    (y - padding),
                    (r + padding),
                    (b + padding)
            ))
        } else {
            rectF.add(RectF(
                    x.toFloat(),
                    y.toFloat(),
                    r.toFloat(),
                    b.toFloat())
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isCircle) {
            canvas.drawCircle(x.toFloat(), y.toFloat(), radius, paintFill)
            canvas.drawCircle(x.toFloat(), y.toFloat(), radius, paintStroke)
        } else {
            for (rect: RectF in rectF) {
                canvas.drawRoundRect(rect, radius, radius, paintFill)
                canvas.drawRoundRect(rect, radius, radius, paintStroke)
            }
        }
    }

    fun clear() {
        rectF.clear()
        isCircle = false
    }
}
