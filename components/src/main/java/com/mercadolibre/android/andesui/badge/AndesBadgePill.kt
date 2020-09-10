package com.mercadolibre.android.andesui.badge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillAttrsParser
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillConfiguration
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillConfigurationFactory
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import java.util.Locale

class AndesBadgePill : CardView {

    /**
     * Getter and setter for [pillHierarchy].
     */
    var pillHierarchy: AndesBadgePillHierarchy
        get() = andesBadgeAttrs.andesBadgePillHierarchy
        set(value) {
            andesBadgeAttrs = andesBadgeAttrs.copy(andesBadgePillHierarchy = value)
            setupColorComponents(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesBadgeType
        get() = andesBadgeAttrs.andesBadgeType
        set(value) {
            andesBadgeAttrs = andesBadgeAttrs.copy(andesBadgeType = value)
            setupColorComponents(createConfig())
        }

    /**
     * Getter and setter for [pillBorder].
     */
    var pillBorder: AndesBadgePillBorder
        get() = andesBadgeAttrs.andesBadgePillBorder
        set(value) {
            andesBadgeAttrs = andesBadgeAttrs.copy(andesBadgePillBorder = value)
            setupColorComponents(createConfig())
        }

    /**
     * Getter and setter for [pillSize].
     */
    var pillSize: AndesBadgePillSize
        get() = andesBadgeAttrs.andesBadgePillSize
        set(value) {
            andesBadgeAttrs = andesBadgeAttrs.copy(andesBadgePillSize = value)
            setupColorComponents(createConfig())
        }

    /**
     * Getter and setter for [text].
     */
    var text: String?
        get() = andesBadgeAttrs.andesBadgeText
        set(value) {
            andesBadgeAttrs = andesBadgeAttrs.copy(andesBadgeText = value)
            setupTitleComponent(createConfig())
        }

    private lateinit var badgeTitle: TextView
    private lateinit var andesBadgeAttrs: AndesBadgePillAttrs

    @Suppress("unused")
    private constructor(context: Context) : super(context) {
        throw IllegalStateException(
                "Constructor without parameters in Andes Badge is not allowed. You must provide some attributes."
        )
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    @Suppress("unused", "LongParameterList")
    constructor(
        context: Context,
        pillHierarchy: AndesBadgePillHierarchy = HIERARCHY_DEFAULT,
        type: AndesBadgeType = STATE_DEFAULT,
        pillBorder: AndesBadgePillBorder = BORDER_DEFAULT,
        pillSize: AndesBadgePillSize = SIZE_DEFAULT,
        text: String? = TEXT_DEFAULT
    ) : super(context) {
        initAttrs(pillHierarchy, type, pillBorder, pillSize, text)
    }

    /**
     * Sets the proper [config] for this message based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesBadgeAttrs = AndesBadgePillAttrsParser.parse(context, attrs)
        val config = AndesBadgePillConfigurationFactory.create(context, andesBadgeAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
        pillHierarchy: AndesBadgePillHierarchy,
        type: AndesBadgeType,
        pillBorder: AndesBadgePillBorder,
        pillSize: AndesBadgePillSize,
        title: String?
    ) {
        andesBadgeAttrs = AndesBadgePillAttrs(pillHierarchy, type, pillBorder, pillSize, title)
        val config = AndesBadgePillConfigurationFactory.create(context, andesBadgeAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this badge.
     * Is like a choreographer ;)
     */
    private fun setupComponents(config: AndesBadgePillConfiguration) {
        cardElevation = CARD_ELEVATION

        initComponents()
        setupViewId()

        setupColorComponents(config)
    }

    private fun setupColorComponents(config: AndesBadgePillConfiguration) {
        setupTitleComponent(config)
        setupBackground(config)
    }

    /**
     * Creates all the views that are part of this badge.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_badge_pill, this)
        badgeTitle = container.findViewById(R.id.andes_badge_text)
    }

    /**
     * Sets a view id to this badge.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    /**
     * Gets data from the config and sets to the title component of this badge.
     */
    private fun setupTitleComponent(config: AndesBadgePillConfiguration) {
        if (config.text == null || config.text.isEmpty()) {
            badgeTitle.visibility = View.GONE
        } else {
            badgeTitle.visibility = View.VISIBLE
            badgeTitle.text = config.text.toUpperCase(Locale.getDefault())
            badgeTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.textSize)
            badgeTitle.setTextColor(config.textColor.colorInt(context))
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.setMargins(config.textMargin, 0, config.textMargin, 0)
            params.gravity = Gravity.CENTER
            badgeTitle.layoutParams = params
        }
    }

    @SuppressWarnings("MagicNumber")
    private fun setupBackground(config: AndesBadgePillConfiguration) {
        val shape = GradientDrawable()

        (shape.mutate() as GradientDrawable).cornerRadii =
                floatArrayOf(config.backgroundRadius[0], config.backgroundRadius[0],
                        config.backgroundRadius[1], config.backgroundRadius[1],
                        config.backgroundRadius[2], config.backgroundRadius[2],
                        config.backgroundRadius[3], config.backgroundRadius[3])

                shape.setColor(config.backgroundColor.colorInt(context))

                background = shape
                if (layoutParams == null) {
                    layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, config.height.toInt())
                }

                minimumWidth = config.height.toInt()
                minimumHeight = config.height.toInt()
    }

    private fun createConfig() = AndesBadgePillConfigurationFactory.create(context, andesBadgeAttrs)

    companion object {
        private const val CARD_ELEVATION = 0F
        private val BORDER_DEFAULT = AndesBadgePillBorder.ROUNDED
        private val HIERARCHY_DEFAULT = AndesBadgePillHierarchy.LOUD
        private val SIZE_DEFAULT = AndesBadgePillSize.SMALL
        private val STATE_DEFAULT = AndesBadgeType.NEUTRAL
        private val TEXT_DEFAULT = null
    }
}
