package com.mercadolibre.android.andesui.textfield.factory

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

/**
 * The data class that contains the public components of the textfield.
 */
internal data class AndesTextfieldAttrs(
    val label: String?,
    val helper: String?,
    val placeholder: String?,
    val counter: Int,
    val showCounter: Boolean = true,
    val state: AndesTextfieldState,
    val leftContent: AndesTextfieldLeftContent?,
    val rightContent: AndesTextfieldRightContent?,
    val inputType: Int
)

/**
 * This object parse the attribute set and return an instance of AndesMessageAttrs to be used by AndesMessage
 */
internal object AndesTextfieldAttrsParser {

    private const val ANDES_TEXTFIELD_CONTENT_PREFIX = "10"
    private const val ANDES_TEXTFIELD_CONTENT_SUFFIX = "11"
    private const val ANDES_TEXTFIELD_CONTENT_ICON = "12"
    private const val ANDES_TEXTFIELD_CONTENT_TOOLTIP = "13"
    private const val ANDES_TEXTFIELD_CONTENT_VALIDATED = "14"
    private const val ANDES_TEXTFIELD_CONTENT_CLEAR = "15"
    private const val ANDES_TEXTFIELD_CONTENT_ACTION = "16"
    private const val ANDES_TEXTFIELD_CONTENT_INDETERMINATE = "17"
    private const val ANDES_TEXTFIELD_CONTENT_CHECKBOX = "18"

    private const val ANDES_TEXTFIELD_STATE_ENABLED = "20"
    private const val ANDES_TEXTFIELD_STATE_ERROR = "21"
    private const val ANDES_TEXTFIELD_STATE_DISABLED = "22"
    private const val ANDES_TEXTFIELD_STATE_READONLY = "23"

    fun parse(context: Context, attr: AttributeSet?): AndesTextfieldAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesTextfield)

        val state = parseState(typedArray)
        val leftContent = parseLeftContent(typedArray)
        val rightContent = parseRightContent(typedArray)
        val inputType = typedArray.getInt(R.styleable.AndesTextfield_android_inputType, InputType.TYPE_CLASS_TEXT)

        return AndesTextfieldAttrs(
            label = typedArray.getString(R.styleable.AndesTextfield_andesTextfieldLabel),
            helper = typedArray.getString(R.styleable.AndesTextfield_andesTextfieldHelper),
            placeholder = typedArray.getString(R.styleable.AndesTextfield_andesTextfieldPlaceholder),
            counter = typedArray.getInt(R.styleable.AndesTextfield_andesTextfieldCounter, 0),
            state = state,
            leftContent = leftContent,
            rightContent = rightContent,
            inputType = inputType
        ).also { typedArray.recycle() }
    }

    private fun parseState(typedArray: TypedArray): AndesTextfieldState {
        return when (typedArray.getString(R.styleable.AndesTextfield_andesTextfieldState)) {
            ANDES_TEXTFIELD_STATE_ENABLED -> AndesTextfieldState.IDLE
            ANDES_TEXTFIELD_STATE_ERROR -> AndesTextfieldState.ERROR
            ANDES_TEXTFIELD_STATE_DISABLED -> AndesTextfieldState.DISABLED
            ANDES_TEXTFIELD_STATE_READONLY -> AndesTextfieldState.READONLY
            else -> AndesTextfieldState.IDLE
        }
    }

    private fun parseLeftContent(typedArray: TypedArray): AndesTextfieldLeftContent? {
        return when (typedArray.getString(R.styleable.AndesTextfield_andesTextfieldLeftContent)) {
            ANDES_TEXTFIELD_CONTENT_PREFIX -> AndesTextfieldLeftContent.PREFIX
            ANDES_TEXTFIELD_CONTENT_ICON -> AndesTextfieldLeftContent.ICON
            else -> null
        }
    }

    private fun parseRightContent(typedArray: TypedArray): AndesTextfieldRightContent? {
        return when (typedArray.getString(R.styleable.AndesTextfield_andesTextfieldRightContent)) {
            ANDES_TEXTFIELD_CONTENT_SUFFIX -> AndesTextfieldRightContent.SUFFIX
            ANDES_TEXTFIELD_CONTENT_ICON -> AndesTextfieldRightContent.ICON
            ANDES_TEXTFIELD_CONTENT_TOOLTIP -> AndesTextfieldRightContent.TOOLTIP
            ANDES_TEXTFIELD_CONTENT_VALIDATED -> AndesTextfieldRightContent.VALIDATED
            ANDES_TEXTFIELD_CONTENT_CLEAR -> AndesTextfieldRightContent.CLEAR
            ANDES_TEXTFIELD_CONTENT_ACTION -> AndesTextfieldRightContent.ACTION
            ANDES_TEXTFIELD_CONTENT_INDETERMINATE -> AndesTextfieldRightContent.INDETERMINATE
            ANDES_TEXTFIELD_CONTENT_CHECKBOX -> AndesTextfieldRightContent.CHECKBOX
            else -> null
        }
    }
}
