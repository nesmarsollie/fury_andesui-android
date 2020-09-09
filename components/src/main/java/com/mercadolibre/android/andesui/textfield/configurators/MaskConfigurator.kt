package com.mercadolibre.android.andesui.textfield.configurators

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.mercadolibre.android.andesui.textfield.AndesTextfield

private const val GENERIC_CHAR = '#'

/**
 * Set mask in text field.
 *
 * @param mask mask pattern ##-###-######-#
 * @param digits sample 0123456789-
 * @param listener A filter to listen when the value has changed
 */
fun AndesTextfield.configureMask(
    mask: String,
    digits: String? = null,
    listener: ((newValue: String) -> Unit)? = null
) {
    this.textWatcher = TextFieldMaskWatcher(mask, listener).also {
        this.counterAdapter = { s ->
            it.cleanMask(s ?: "").length
        }
        if (mask.isNotEmpty()) {
            this.counter = it.cleanMask(mask).length
        }
    }
    this.textDigits = digits
    this.textFilter = InputFilter.LengthFilter((mask.length))
}

/**
 * Expansion to clear an previusly assigned mask.
 * Use this expansion to clean mask, watchers, digits.
 */
fun AndesTextfield.clearMask() {
    this.textWatcher = null
    this.counterAdapter = null
    this.textDigits = null
    this.textFilter = null
    this.counter = 0
}

/**
 * This mask watcher formats the input with the provided mask.
 * It also calls the text change function
 */
private class TextFieldMaskWatcher(
    private var mask: String = "",
    private var textChange: ((newValue: String) -> Unit)?
) : TextWatcher {
    private var enabled = true

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable) {
        if (!enabled) {
            return
        }

        val text = editable.toString()
        val textFormatted = maskString(text)

        if (text != textFormatted) {
            enabled = false
            editable.replace(0, editable.length, textFormatted)
            enabled = true
        }

        textChange?.invoke(textFormatted)
    }

    /**
     * Applies the mask to the given string.
     * It cleans the text, and applies the mask
     */
    fun maskString(text: String): String {
        if (mask.isEmpty()) {
            return text
        }

        val unformattedText = CharReader(cleanMask(text))
        val maskToProcess = mask

        var result = ""
        for (i in 0..maskToProcess.length) {
            if (!unformattedText.hasMoreChars()) {
                break
            }

            result += if (maskToProcess[i] != GENERIC_CHAR) {
                maskToProcess[i]
            } else {
                unformattedText.nextChar()
            }
        }
        return result
    }

    /**
     * Cleans the text leaving values # according to mask
     */
    fun cleanMask(text: String): String {
        val maskChars = mask.replace(GENERIC_CHAR.toString(), "")
        var textWithoutMask = text
        maskChars.forEach { char ->
            textWithoutMask = textWithoutMask.replace(char.toString(), "")
        }
        return textWithoutMask
    }
}

/**
 * A convenient class to read an string char by char
 */
private class CharReader(val s: String) {
    var index = 0

    fun hasMoreChars() = index < s.length

    fun nextChar(): Char? {
        return if (hasMoreChars()) {
            s[index].also {
                index++
            }
        } else {
            null
        }
    }
}