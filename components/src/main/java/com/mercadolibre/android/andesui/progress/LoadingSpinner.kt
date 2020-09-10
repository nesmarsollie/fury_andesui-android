package com.mercadolibre.android.andesui.progress

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorRes
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.mercadolibre.android.andesui.R

@Suppress("TooManyFunctions")
class LoadingSpinner @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var primaryColor: Paint
    @SuppressWarnings("MagicNumber")
    private var sweepAngle = 0
    @SuppressWarnings("MagicNumber")
    private var startAngle = 0
    @SuppressWarnings("MagicNumber")
    private var strokeSize = 5
    private lateinit var viewBounds: RectF
    private lateinit var currentColor: Paint
    private lateinit var sweepAnim: ValueAnimator
    private lateinit var startAnim: ValueAnimator
    private lateinit var finalAnim: ValueAnimator

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        bindAttrs()
        createAnimators()
    }

    private fun bindAttrs() {
        val primaryColorInt = resources.getColor(R.color.andes_blue_ml_500)
        primaryColor = createPaint(Paint.Style.STROKE, strokeSize, primaryColorInt)
    }

    /**
     * Configure the animations that interpolate the arc values to achieve the loading effect
     */
    private fun setupListeners() {
        cleanListeners()

        sweepAnim.addUpdateListener { animation -> sweepAngle = animation.animatedValue as Int }
        startAnim.addUpdateListener { animation ->
            startAngle = animation.animatedValue as Int
            invalidate()
        }
        finalAnim.addUpdateListener { animation ->
            startAngle = animation.animatedValue as Int
            invalidate()
        }
        finalAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                sweepAnim.start()
                startAnim.start()
            }
        })
        sweepAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                finalAnim.start()
            }
        })
    }

    /**
     * Create an animator that will interpolate the angles of the circle
     *
     * @param startAngle the start value of the angle in degrees. Eg: 0
     * @param endAngle the end value of the angle in degrees. Eg: 270
     * @param duration the duration of the animation
     * @return an animator that will interpolate the angles between startAngle and endAngle
     */
    private fun createAnimator(startAngle: Int, endAngle: Int, duration: Int): ValueAnimator {
        val animator = ValueAnimator.ofInt(startAngle, endAngle)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration.toLong()
        return animator
    }

    /**
     * Create the paint that will be used to draw the view
     *
     * @param style the paint style
     * @param strokeWidth the stroke width
     * @param hex the color to paint
     * @return the paint to apply
     */
    private fun createPaint(style: Paint.Style, strokeWidth: Int, hex: Int): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = style
        paint.strokeWidth = strokeWidth.toFloat()
        paint.color = hex
        return paint
    }

    /**
     * When the view size changes, calculate its new size and start rotating from the center
     * {@inheritDoc}
     */
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        viewBounds = RectF(strokeSize.toFloat(), strokeSize.toFloat(), (width - strokeSize).toFloat(), (height - strokeSize).toFloat())
        if (this.animation != null) {
            this.animation.cancel()
        }
        val rotateAnimation = RotateAnimation(0f, FULL_CIRCLE.toFloat(), viewBounds.centerX(), viewBounds.centerY())
        rotateAnimation.duration = DURATION
        rotateAnimation.repeatCount = -1
        rotateAnimation.repeatMode = Animation.RESTART
        rotateAnimation.interpolator = LinearInterpolator()
        startAnimation(rotateAnimation)
    }

    /**
     * Draw the arc of the loading progress
     * {@inheritDoc}
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (visibility == VISIBLE) {
            canvas.drawArc(viewBounds, startAngle.toFloat(), sweepAngle - startAngle.toFloat(), false, currentColor)
        }
    }

    /**
     * Call this on activity start to begin animations
     */
    fun start() {
        if (!startAnim.isRunning) {
            setupListeners()
            sweepAnim.start()
            startAnim.start()
        }
    }

    /**
     * Call this on activity stop to finish animations
     */
    fun stop() {
        cleanListeners()
    }

    /**
     * Clean the animators so as not to leak memory
     *
     * @param animator the animator to clean
     */
    private fun cleanAnimator(animator: ValueAnimator) {
        animator.cancel()
        animator.removeAllListeners()
        animator.removeAllUpdateListeners()
    }

    /**
     * Set the primary color of the loading wheel. Default is meli blue.
     *
     * @param colorId the color resource id
     */
    fun setPrimaryColor(@ColorRes colorId: Int) {
        primaryColor = createPaint(Paint.Style.STROKE, strokeSize, colorId)
        currentColor = primaryColor
    }

    /**
     * Set the stroke size in pixels.
     * This has to be called before [.setPrimaryColor]
     * to have any effect.
     *
     * @param strokeSize The new stroke size in pixels.
     */
    fun setStrokeSize(strokeSize: Int) {
        this.strokeSize = strokeSize
    }

    private fun cleanListeners() {
        cleanAnimator(sweepAnim)
        cleanAnimator(startAnim)
        cleanAnimator(finalAnim)
    }

    private fun createAnimators() {
        sweepAnim = createAnimator(0, FULL_CIRCLE, Companion.DEFAULT_DURATION)
        startAnim = createAnimator(0, QUARTER_CIRCLE, Companion.DEFAULT_DURATION)
        finalAnim = createAnimator(QUARTER_CIRCLE, FULL_CIRCLE, Companion.DEFAULT_DURATION)
    }

    companion object {
        const val FULL_CIRCLE = 360
        const val QUARTER_CIRCLE = 90
        const val DURATION = 2000L
        private const val DEFAULT_DURATION = 750
    }

    init {
        init(attrs, defStyleAttr)
    }
}
