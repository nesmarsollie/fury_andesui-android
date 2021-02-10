package com.mercadolibre.android.andesui.coachmark.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.core.widget.NestedScrollView
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.CoachmarkTracking
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.presenter.CoachmarkPresenter
import com.mercadolibre.android.andesui.coachmark.presenter.CoachmarkViewInterface
import com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage.WalkthroughMessageView
import com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless.WalkthroughScrollessMessageView

@SuppressWarnings("TooManyFunctions")
class CoachmarkView private constructor(builder: Builder) : CoachmarkViewInterface {
    private val activity: Activity
    private val presenter: CoachmarkPresenter
    private val coachmarkOverlayView: CoachmarkOverlay
    private val baseContainer: FrameLayout
    private val scrollView: NestedScrollView
    private val walkthroughMessageView: WalkthroughMessageView
    private val coachmarkContainer: CoachmarkContainerView
    private var previousOrientationScreen = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    private var lastPosition = false

    init {
        val coachmarkData = builder.coachmarkData
        activity = builder.activity
        scrollView = coachmarkData.scrollView
        walkthroughMessageView = WalkthroughMessageView(activity)
        baseContainer = FrameLayout(activity)
        coachmarkContainer = CoachmarkContainerView(activity)
        coachmarkOverlayView = coachmarkContainer.findViewById(R.id.coachmarkOverlayView)

        presenter = CoachmarkPresenter(this)

        initContainer()
        setNextView(0, coachmarkData)
        initListeners(coachmarkData, builder.onTrackingListener)
    }

    private fun initContainer() {
        previousOrientationScreen = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        baseContainer.setBackgroundColor(ContextCompat.getColor(activity, R.color.andes_transparent))
        baseContainer.isClickable = true
        baseContainer.visibility = View.GONE
        baseContainer.alpha = 0f
        baseContainer.fitsSystemWindows = true
        walkthroughMessageView.alpha = 0f

        // Crea vista por encima de lo que esta visible
        if (activity.window != null) {
            val decorView = activity.window.decorView as ViewGroup?
            decorView?.let {
                val content = it.findViewById<View>(android.R.id.content) as ViewGroup?
                content?.let {
                    baseContainer.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, decorView.getChildAt(0).height)
                    decorView.addView(baseContainer)
                    coachmarkOverlayView.setBackgroundColor(ContextCompat.getColor(activity, R.color.andes_gray_900))
                    baseContainer.addView(coachmarkContainer)
                    baseContainer.addView(walkthroughMessageView)
                }
            }
        }

        baseContainer.visibility = View.VISIBLE
        ViewCompat.animate(baseContainer)
            .alpha(1f)
            .setDuration(ANIMATION_OVERLAY_DURATION)
            .start()
    }

    /**
     * Setea los comportamientos que van a tener los distintos botones del tooltip y utiliza
     * el listener del trackeo para dar aviso de ello
     *
     * @param coachmarkData es el que contiene todos los datos para darle al step anterior o siguiente
     * @param onTrackingListener listener para avisar sobre el trackeo cuando se hace click en los diferentes botones
     */
    private fun initListeners(
        coachmarkData: AndesWalkthroughCoachmark,
        onTrackingListener: CoachmarkTracking?
    ) {

        coachmarkContainer.setListener(object : CoachmarkContainerView.CoachmarkContainerListener {
            override fun onClickClose(position: Int) {

                onTrackingListener?.onClose(position)
                dismiss(coachmarkData.completionHandler)
            }
        })

        walkthroughMessageView.setListener(object : WalkthroughScrollessMessageView.WalkthroughButtonClicklistener {
            override fun onClickNextButton(position: Int) {

                if (coachmarkData.steps.size == position + 1) {
                    dismiss(coachmarkData.completionHandler)
                } else {
                    walkthroughMessageView.animate()
                            .alpha(0f)
                            .setDuration(ANIMATION_TOOLTIP_DURARION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    super.onAnimationEnd(animation)

                                    lastPosition = (coachmarkData.steps.size - 1) == (position + 1)
                                    walkthroughMessageView.clearAnimation()
                                    presenter.restorePreviousValues()
                                    setNextView(position + 1, coachmarkData)
                                }
                            })
                            .start()
                }
                onTrackingListener?.onNext(position)
            }
        })

    }

    /**
     * Prepara vistas a referenciar y setea informacion al tooltip
     *
     * @param position posicion del step a referenciar
     * @param coachmarkData informacion necesaria para resaltar el siguiente step
     */
    private fun setNextView(position: Int, coachmarkData: AndesWalkthroughCoachmark) {

        val stepReferenced = coachmarkData.steps[position]
        walkthroughMessageView.setPosition(position)
        coachmarkContainer.setData(position, coachmarkData.steps.size)

        // Posiciona el scroll en donde este la vista
        scrollView.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                scroll(stepReferenced)
                scrollView.viewTreeObserver?.removeOnPreDrawListener(this)
                return false
            }
        })
    }

    private fun scroll(stepReferenced: AndesWalkthroughCoachmarkStep) {

        val stepReferenceGlobalRect = Rect()
        stepReferenced.view?.getGlobalVisibleRect(stepReferenceGlobalRect)

        val overlayRect = Rect()
        coachmarkOverlayView.getGlobalVisibleRect(overlayRect)

        walkthroughMessageView.definePosition(overlayRect, stepReferenceGlobalRect)
        walkthroughMessageView.setData(WalkthroughMessageModel(stepReferenced.title, stepReferenced.description,
        stepReferenced.nextText), lastPosition)

        walkthroughMessageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                walkthroughMessageView.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                presenter.resolveScrollMode(
                    stepReferenced,
                    coachmarkOverlayView.height,
                    stepReferenced.view?.height ?: 0,
                    stepReferenceGlobalRect,
                    overlayRect,
                    walkthroughMessageView.getChildAt(0).height,
                    walkthroughMessageView.getPosition())
            }
        })
    }

    /**
     * Realiza la animacion del scroll para que no sea brusca
     *
     * @param isVisible si esta visible determina que no debe realizar la animacion
     * @param scrollToY posicion hacia la que debe scrollear
     * @param stepReferenced step referenciado al que luego debe ser resaltado
     */
    override fun animateScroll(isVisible: Boolean, scrollToY: Int, stepReferenced: AndesWalkthroughCoachmarkStep) {
        val yTranslate = ObjectAnimator.ofInt(scrollView, "scrollY", scrollToY)
        val animators = AnimatorSet()

        animators.duration = ANIMATION_SCROLL_DURATION

        if (!isVisible) {
            animators.playTogether(yTranslate)
        }

        animators.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(arg0: Animator) {
                // Nothing to do
            }

            override fun onAnimationRepeat(arg0: Animator) {
                // Nothing to do
            }

            override fun onAnimationEnd(arg0: Animator) {
                addTarget(stepReferenced)
            }

            override fun onAnimationCancel(arg0: Animator) {
                // Nothing to do
            }
        })
        animators.start()
    }

    /**
     * Es el encargado de hacer los recortes en el overlay
     *
     * @param stepReferenced contiene los datos los elementos a resaltar
     */
    override fun addTarget(stepReferenced: AndesWalkthroughCoachmarkStep) {

        stepReferenced.view?.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                coachmarkOverlayView.clear()
                presenter.addRect(stepReferenced)
                coachmarkOverlayView.postInvalidate()
                setTooltipAlignment(stepReferenced)
                stepReferenced.view?.viewTreeObserver?.removeOnPreDrawListener(this)
                return false
            }
        })
        stepReferenced.view?.postInvalidate()
    }

    /**
     * Agrega las vistas a ser recortadas circular
     *
     * @param stepReferenced view a ser resaltado en coachmark
     */
    override fun addCircleRect(stepReferenced: AndesWalkthroughCoachmarkStep) {

        val rect = Rect()
        stepReferenced.view?.getGlobalVisibleRect(rect)
        val cx = rect.centerX()
        val cy = rect.centerY()

        val radius = (Math.max(rect.width(), rect.height()) / 2f).toInt() +
            activity.resources.getDimension(R.dimen.andes_coachmark_padding_internal_overlay)

        coachmarkOverlayView.addRect(
            cx,
            cy - activity.resources.getDimension(R.dimen.andes_coachmark_toolbar_status_bar).toInt(),
            0,
            0,
            true,
            radius
        )
    }

    /**
     * Agrega las vistas a ser recortadas
     *
     * @param stepReferenced view a ser resaltado en coachmark
     */
    override fun addRoundRect(stepReferenced: AndesWalkthroughCoachmarkStep) {

        val rect = Rect()
        stepReferenced.view?.getGlobalVisibleRect(rect)
        coachmarkOverlayView.addRect(
            rect.left,
            rect.top - activity.resources.getDimension(R.dimen.andes_coachmark_toolbar_status_bar).toInt(),
            rect.width(),
            rect.height(),
            false
        )
    }

    private fun setTooltipAlignment(stepReferenced: AndesWalkthroughCoachmarkStep) {
        walkthroughMessageView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {

                val tooltipHeight = walkthroughMessageView.getChildAt(0).height
                val targetRect = Rect()
                stepReferenced.view?.getGlobalVisibleRect(targetRect)
                presenter.relocateTooltip(tooltipHeight, walkthroughMessageView.getPosition(), targetRect)

                walkthroughMessageView.animate()
                    .alpha(1f)
                    .setDuration(ANIMATION_TOOLTIP_DURARION)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            walkthroughMessageView.clearAnimation()
                        }
                    })
                    .start()
                walkthroughMessageView.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })
    }

    override fun getFooterHeigh(): Int {
        return activity.resources.getDimensionPixelSize(R.dimen.andes_coachmark_footer_guide_line)
    }

    override fun getToolbarSize(): Int {
        return activity.resources.getDimensionPixelSize(R.dimen.andes_coachmark_toolbar_status_bar)
    }

    override fun getTooltipMargin(): Int {
        return activity.resources.getDimensionPixelSize(R.dimen.andes_coachmark_walkthrought_margin)
    }

    override fun getScrollViewPaddingFromDimen(): Int {
        return activity.resources.getDimensionPixelSize(R.dimen.andes_coachmark_default_padding)
    }

    /**
     * Setea al tooltipView la posicion en la que debe estar en el eje de Y, luego actualiza la vista
     *
     * @param positionY posicion Y a ser colocado el tooltip
     */
    override fun setWalkthroughMessageViewY(positionY: Float) {
        walkthroughMessageView.y = positionY
        walkthroughMessageView.postInvalidate()
    }

    override fun scrollTo(scrollToY: Int) {
        scrollView.scrollTo(0, scrollToY)
    }

    override fun setScrollViewPaddings(left: Int, top: Int, right: Int, bottom: Int) {
        scrollView.setPadding(left, top, right, bottom)
        scrollView.postInvalidate()
    }

    override fun clearWalkthroughMessageView() {
        walkthroughMessageView.clear()
    }

    /**
     * Elimina los recortes de las vistas que tiene el overlay
     */
    override fun cleanCoachmarkOverlayView() {
        coachmarkOverlayView.clear()
        coachmarkOverlayView.postInvalidate()
    }

    /**
     * Se encarga de dejar la panralla sin el coachmark y luego avisa mediante el listener que termino
     *
     * @param onAfterDismissListener listener que ejecuta luego de quitar coachmark
     */
    fun dismiss(onAfterDismissListener: (() -> Unit)?) {

        walkthroughMessageView.visibility = View.GONE
        activity.requestedOrientation = previousOrientationScreen
        presenter.restorePreviousValues()
        ViewCompat.animate(baseContainer)
            .alpha(0f)
            .setDuration(ANIMATION_OVERLAY_DURATION)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View?) {
                    super.onAnimationEnd(view)
                    if (baseContainer.alpha == 0f) {
                        val parent = view?.parent
                        (parent as? ViewGroup)?.removeView(view)
                        onAfterDismissListener?.invoke()
                    }
                }
            }).start()
    }

    class Builder(val activity: Activity, val coachmarkData: AndesWalkthroughCoachmark) {
        internal var onTrackingListener: CoachmarkTracking? = null

        fun withTrackingListener(onTrackingListener: CoachmarkTracking): Builder {
            this.onTrackingListener = onTrackingListener
            return this
        }

        fun build(): CoachmarkView {
            return CoachmarkView(this)
        }
    }

    companion object {
        private const val ANIMATION_TOOLTIP_DURARION = 500L
        private const val ANIMATION_OVERLAY_DURATION = 400L
        private const val ANIMATION_SCROLL_DURATION = 1000L
    }
}
