package com.mercadolibre.android.andesui.dropdown.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.utils.ScreenUtils

class DropdownBottomSheetDialog(
        context: Context,
        theme: Int,
        private val andesListDelegate: AndesListDelegate
) : BottomSheetDialog(context, theme) {
    private var containerView: ConstraintLayout? = null
    private var dragIndicator: View? = null
    internal var andesList: AndesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LayoutInflater.from(context).inflate(R.layout.andes_layout_dropdown_bottom_sheet, null)
        setContentView(layout)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        containerView = findViewById(R.id.andes_dropdown_bottom_sheet_container)
        dragIndicator = findViewById(R.id.andes_bottom_sheet_drag_indicator)
        andesList = findViewById(R.id.andesListDropdown)

        andesList?.delegate = andesListDelegate

        initBottomSheetBehavior()
        resolveDragIndicator()
        resolveBottomSheetBackground()
    }

    private fun resolveDragIndicator() {
        val cornerRadius = context.resources.getDimension(R.dimen.andes_bottom_sheet_drag_indicator_corner_radius)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(ResourcesCompat.getColor(context.resources, R.color.andes_gray_250, context.theme))
        shape.cornerRadius = cornerRadius

        dragIndicator?.background = shape
    }

    private fun resolveBottomSheetBackground() {
        val cornerRadius = context.resources.getDimension(R.dimen.andes_bottom_sheet_default_radius)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f, 0f, 0f)
        shape.setColor(ResourcesCompat.getColor(context.resources, R.color.andes_white, context.theme))

        containerView?.background = shape
    }

    private fun initBottomSheetBehavior() {
        val bottomSheet = findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = ScreenUtils.getScreenHeight() / 2
    }

}
