package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.coachmark.CoachmarkTracking
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.model.AndesScrollessWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.presenter.CoachmarkPresenter
import com.mercadolibre.android.andesui.coachmark.presenter.CoachmarkViewInterface
import com.mercadolibre.android.andesui.coachmark.view.CoachmarkOverlay

@SuppressWarnings("TooManyFunctions")
class CoachmarkScrollessView private constructor(builder: Builder) : CoachmarkViewInterface {
    private val activity: Activity
    private val presenter: CoachmarkPresenter
    private val coachmarkOverlayView: CoachmarkOverlay
    private val baseContainer: FrameLayout
    private val view: View
    private val walkthroughScrollessMessageView: WalkthroughScrollessMessageView
    private val coachmarkContainer: CoachmarkScrollessContainerView
    private var previousOrientationScreen = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    private var lastPosition = false
    private val statusBarColor: Int

    init {
        val coachmarkData = builder.coachmarkData
        coachmarkData.steps = filterEmptySteps(builder.coachmarkData) as MutableList<AndesWalkthroughCoachmarkStep>
        activity = builder.activity
        statusBarColor = getStatusBarColor()
        view = coachmarkData.anchorView
        walkthroughScrollessMessageView = WalkthroughScrollessMessageView(activity)
        baseContainer = FrameLayout(activity)
        coachmarkContainer = CoachmarkScrollessContainerView(activity)
        coachmarkOverlayView = coachmarkContainer.findViewById(R.id.coachmarkOverlayView)

        presenter = CoachmarkPresenter(this)

        initContainer()
        setNextView(0, coachmarkData)
        initListeners(coachmarkData, builder.onTrackingListener)
        changeStatusBarColor(R.color.andes_gray_950)
    }

    private fun filterEmptySteps(coachmarkData: AndesScrollessWalkthroughCoachmark) : List<AndesWalkthroughCoachmarkStep> {
        return coachmarkData.steps.toList().filter { coachmarkStep ->
            coachmarkStep.view != null
        }
    }

    private fun initContainer() {
        previousOrientationScreen = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        baseContainer.setBackgroundColor(ContextCompat.getColor(activity, R.color.andes_transparent))
        baseContainer.isClickable = true
        baseContainer.visibility = View.GONE
        baseContainer.alpha = 0f
        baseContainer.fitsSystemWindows = true
        walkthroughScrollessMessageView.alpha = 0f

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
                    baseContainer.addView(walkthroughScrollessMessageView)
                }
            }
        }

        baseContainer.visibility = View.VISIBLE
        ViewCompat.animate(baseContainer)
                .alpha(1f)
                .setDuration(ANIMATION_OVERLAY_DURATION)
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationEnd(view: View?) {
                        // needed for accessibility
                        coachmarkContainer.requestFocus()
                    }

                    override fun onAnimationCancel(view: View?) {
                        // no-op
                    }

                    override fun onAnimationStart(view: View?) {
                        // no-op
                    }
                })
                .start()
    }

    /**
     * Setea los comportamientos que van a tener los distintos botones del tooltip y utiliza
     * el listener del trackeo para dar aviso de ello
     *
     * @param coachmarkData es el que contiene todos los datos para darle al step anterior o siguiente
     * @param onTrackingListener listener para avisar sobre el trackeo cuando se hace click en los diferentes botones
     */
    private fun initListeners(coachmarkData: AndesScrollessWalkthroughCoachmark,
            onTrackingListener: CoachmarkTracking?
    ) {

        coachmarkContainer.setListener(object : CoachmarkScrollessContainerView.CoachmarkContainerListener {
            override fun onClickClose(position: Int) {
                onTrackingListener?.onClose(position)
                dismiss(coachmarkData.completionHandler)
            }
        })

        walkthroughScrollessMessageView.setListener(object : WalkthroughScrollessMessageView.WalkthroughButtonClicklistener {
            override fun onClickNextButton(position: Int) {
                if (coachmarkData.steps.size == position + 1) {
                    dismiss(coachmarkData.completionHandler)
                } else {
                    walkthroughScrollessMessageView.animate()
                            .alpha(0f)
                            .setDuration(ANIMATION_TOOLTIP_DURARION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    super.onAnimationEnd(animation)

                                    lastPosition = (coachmarkData.steps.size - 1) == (position + 1)
                                    walkthroughScrollessMessageView.clearAnimation()
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
    private fun setNextView(position: Int, coachmarkData: AndesScrollessWalkthroughCoachmark) {
        val stepReferenced = coachmarkData.steps[position]
        walkthroughScrollessMessageView.setPosition(position)
        coachmarkContainer.setData(position, coachmarkData.steps.size)

        val list = RecyclerView(activity.applicationContext)
        list.viewTreeObserver?.addOnDrawListener {
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    scroll(stepReferenced)
                    list.viewTreeObserver?.removeOnPreDrawListener(this)
                    return false
                }
            }
        }

        // Posiciona el scroll en donde este la vista
        view.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                scroll(stepReferenced)
                view.viewTreeObserver?.removeOnPreDrawListener(this)
                return false
            }
        })
    }

    private fun getStatusBarColor() : Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return activity.window?.statusBarColor ?: activity.resources.getColor(R.color.andes_accent_color_500)
        }
        return activity.resources.getColor(R.color.andes_accent_color_500)
    }

    private fun changeStatusBarColor(statusBarColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = statusBarColor
        }
    }

    private fun scroll(stepReferenced: AndesWalkthroughCoachmarkStep) {

        if (stepReferenced.style == AndesWalkthroughCoachmarkStyle.HAMBURGER){
            stepReferenced.view = coachmarkContainer.getHamburgerView()
        }

        val stepReferenceGlobalRect = Rect()
        stepReferenced.view?.getGlobalVisibleRect(stepReferenceGlobalRect)

        val overlayRect = Rect()
        coachmarkOverlayView.getGlobalVisibleRect(overlayRect)

        walkthroughScrollessMessageView.definePosition(overlayRect, stepReferenceGlobalRect)
        walkthroughScrollessMessageView.setData(WalkthroughMessageModel(stepReferenced.title,
                stepReferenced.description, stepReferenced.nextText), lastPosition)

        walkthroughScrollessMessageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                walkthroughScrollessMessageView.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                presenter.resolveScrollMode(
                        stepReferenced,
                        coachmarkOverlayView.height,
                        stepReferenced.view?.height ?: 0,
                        stepReferenceGlobalRect,
                        overlayRect,
                        walkthroughScrollessMessageView.getChildAt(0).height,
                        walkthroughScrollessMessageView.getPosition())
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
        val yTranslate = ObjectAnimator.ofInt(view, "scrollY", scrollToY)
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
                if (stepReferenced.style == AndesWalkthroughCoachmarkStyle.MENU_ITEM
                        || stepReferenced.style == AndesWalkthroughCoachmarkStyle.HAMBURGER) {

                    if (stepReferenced.style == AndesWalkthroughCoachmarkStyle.MENU_ITEM) {
                        coachmarkContainer.getCloseButtonView().visibility = View.GONE
                    }

                    coachmarkContainer.getHeaderView().clear()
                    presenter.addRect(stepReferenced)
                    coachmarkContainer.getHeaderView().postInvalidate()
                } else {
                    coachmarkOverlayView.clear()
                    presenter.addRect(stepReferenced)
                    coachmarkOverlayView.postInvalidate()
                }

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
                radius,
                stepReferenced.showPadding
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

        if(stepReferenced.style == AndesWalkthroughCoachmarkStyle.MENU_ITEM
                || stepReferenced.style == AndesWalkthroughCoachmarkStyle.HAMBURGER) {
            coachmarkContainer.getHeaderView().addRect(
                    rect.left,
                    rect.top - activity.resources.getDimension(R.dimen.andes_coachmark_toolbar_status_bar).toInt() / 3,
                    rect.width(),
                    rect.height(),
                    false,
                    showPadding = stepReferenced.showPadding
            )
        } else {
            coachmarkOverlayView.addRect(
                    rect.left,
                    rect.top - activity.resources.getDimension(R.dimen.andes_coachmark_toolbar_status_bar).toInt(),
                    rect.width(),
                    rect.height(),
                    false,
                    showPadding = stepReferenced.showPadding
            )
        }
    }

    private fun setTooltipAlignment(stepReferenced: AndesWalkthroughCoachmarkStep) {
        walkthroughScrollessMessageView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {

                val tooltipHeight = walkthroughScrollessMessageView.getChildAt(0).height
                val targetRect = Rect()
                stepReferenced.view?.getGlobalVisibleRect(targetRect)
                presenter.relocateTooltip(tooltipHeight, walkthroughScrollessMessageView.getPosition(), targetRect)

                walkthroughScrollessMessageView.animate()
                        .alpha(1f)
                        .setDuration(ANIMATION_TOOLTIP_DURARION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                walkthroughScrollessMessageView.clearAnimation()
                            }
                        })
                        .start()
                walkthroughScrollessMessageView.viewTreeObserver.removeOnPreDrawListener(this)
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
        walkthroughScrollessMessageView.y = positionY
        walkthroughScrollessMessageView.postInvalidate()
    }

    override fun scrollTo(scrollToY: Int) {
        view.scrollTo(0, scrollToY)
    }

    override fun setScrollViewPaddings(left: Int, top: Int, right: Int, bottom: Int) {
        view.setPadding(left, top, right, bottom)
        view.postInvalidate()
    }

    override fun clearWalkthroughMessageView() {
        walkthroughScrollessMessageView.clear()
    }

    /**
     * Elimina los recortes de las vistas que tiene el overlay
     */
    override fun cleanCoachmarkOverlayView() {
        coachmarkContainer.getCloseButtonView().visibility = View.VISIBLE
        coachmarkContainer.getHeaderView().clear()
        coachmarkContainer.getHeaderView().invalidate()
        coachmarkOverlayView.clear()
        coachmarkOverlayView.postInvalidate()
    }

    /**
     * Se encarga de dejar la panralla sin el coachmark y luego avisa mediante el listener que termino
     *
     * @param onAfterDismissListener listener que ejecuta luego de quitar coachmark
     */
    fun dismiss(onAfterDismissListener: (() -> Unit)?) {
        changeStatusBarColor(statusBarColor)
        walkthroughScrollessMessageView.visibility = View.GONE
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

    class Builder(val activity: Activity, val coachmarkData: AndesScrollessWalkthroughCoachmark) {
        internal var onTrackingListener: CoachmarkTracking? = null

        fun withTrackingListener(onTrackingListener: CoachmarkTracking): Builder {
            this.onTrackingListener = onTrackingListener
            return this
        }

        fun build(): CoachmarkScrollessView {
            return CoachmarkScrollessView(this)
        }
    }

    companion object {
        private const val ANIMATION_TOOLTIP_DURARION = 500L
        private const val ANIMATION_OVERLAY_DURATION = 400L
        private const val ANIMATION_SCROLL_DURATION = 1000L
    }
}