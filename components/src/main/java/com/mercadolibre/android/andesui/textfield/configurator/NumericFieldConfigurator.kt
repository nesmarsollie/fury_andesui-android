package com.mercadolibre.android.andesui.textfield.configurator

import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Configure AndesTextfield to allow import input.
 * The input accepts only digits.
 * The input is masked as numbers with grouping separators.
 *
 * @param symbols The internationalization symbols
 * @param showDecimals Allow use decimals (only 2 decimals only)
 */
fun AndesTextfield.configureAsNumericField(
    symbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.getDefault()),
    showDecimals: Boolean = true
) {
    val amountFilter = AmountInputFilter(symbols, showDecimals)
    this.textDigits = "0123456789,."
    this.textFilter = amountFilter
    this.textWatcher = NumberWatcher(amountFilter, "#,##0.##", symbols)
}

private class NumberWatcher(
    val replaceFilter: AmountInputFilter,
    decimalMask: String,
    val decimalFormatSymbols: DecimalFormatSymbols
) : TextWatcher {
    var enabled = true
        set(value) {
            field = value
            replaceFilter.enabled = field
        }

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable) {
        if (!enabled) {
            return
        }

        val s = editable.toString()
        val amountFormatted = formatDouble(parseDouble(s))
        if (shouldUpdate(s, amountFormatted)) {
            enabled = false
            editable.replace(0, editable.length, amountFormatted)
            enabled = true
        }
    }

    val decimalFormat = DecimalFormat(decimalMask, decimalFormatSymbols)

    private fun formatDouble(value: Double): String? {
        if (value == 0.0) {
            return ""
        }
        decimalFormat.roundingMode = RoundingMode.DOWN
        return decimalFormat.format(value)
    }

    private fun parseDouble(value: String?): Double {
        return try {
            val number = value?.replace(
                decimalFormat.decimalFormatSymbols.groupingSeparator.toString(), "")
                .nullIfNullOrEmpty()
                ?: return 0.0
            decimalFormat.roundingMode = RoundingMode.DOWN
            decimalFormat.parse(number)?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }

    private fun shouldUpdate(oldText: String, newText: String?): Boolean {
        var old = oldText.nullIfNullOrEmpty()
        if (old?.contains(decimalFormatSymbols.decimalSeparator) == true) {
            old = old.substring(0, old.indexOf(decimalFormatSymbols.decimalSeparator))
        }

        var new = newText.nullIfNullOrEmpty()
        if (new?.contains(decimalFormatSymbols.decimalSeparator) == true) {
            new = new.substring(0, new.indexOf(decimalFormatSymbols.decimalSeparator))
        }

        return old != new
    }

    fun String?.nullIfNullOrEmpty(): String? {
        if (this.isNullOrEmpty()) {
            return null
        }
        return this
    }
}

/**
 * A filter for input texts based on regex
 */
private class AmountInputFilter(
    val symbols: DecimalFormatSymbols,
    showDecimals: Boolean = true
) : InputFilter {
    private val pattern: Pattern
    var enabled = true

    init {
        var exp = "^([0-9|${symbols.groupingSeparator}]{0,11})?"
        if (showDecimals) {
            exp += "(\\${symbols.decimalSeparator}[0-9]{0,2})?"
        }
        exp += "\$"
        pattern = Pattern.compile(exp)
    }

    override fun filter(
        source: CharSequence,
        start: Int, end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (!enabled) {
            return null
        }
        val src = source.toString().replace(symbols.groupingSeparator, symbols.decimalSeparator)

        val builder = StringBuilder(dest)
        builder.replace(dstart, dend, src.subSequence(start, end).toString())
        val matcher: Matcher = pattern.matcher(builder.toString())
        return if (!matcher.matches()) "" else src
    }
}
