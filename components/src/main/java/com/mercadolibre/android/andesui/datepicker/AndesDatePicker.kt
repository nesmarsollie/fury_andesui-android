package com.mercadolibre.android.andesui.datepicker

import android.content.Context
import android.os.Build
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.datepicker.factory.AndesDatePickerAttrParser
import com.mercadolibre.android.andesui.datepicker.factory.AndesDatePickerAttrs
import com.mercadolibre.android.andesui.datepicker.factory.AndesDatePickerConfiguration
import com.mercadolibre.android.andesui.datepicker.factory.AndesDatePickerConfigurationFactory
import kotlinx.android.synthetic.main.andes_layout_datepicker.view.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


/**
 * Warning TooManyFunctions suppressed because of...
 * It was not possible to reduce the total of functions to 11, since they are all necessary for the setting and validation of the component
 */
@Suppress("TooManyFunctions")
class AndesDatePicker : ConstraintLayout {

    interface ApplyDatePickerClickListener {

        fun onDateApply(date: Calendar)
    }

    var listener: ApplyDatePickerClickListener? = null

    /**
     * Getter and setter for [text].
     **/
    var text: String?
        get() = andesDatePickerAttrs.andesDatePickerText
        set(value) {
            andesDatePickerAttrs = andesDatePickerAttrs.copy(andesDatePickerText = value)
        }

    /**
     * Getter and setter for [minDate].
     */
    var minDate: String?
        get() = andesDatePickerAttrs.andesDatePickerMinDate
        set(value) {
            andesDatePickerAttrs = andesDatePickerAttrs.copy(andesDatePickerMinDate = value)
            setupComponents(createConfig())
        }

    /**
     * Getter and setter for [maxDate].
     */
    var maxDate: String?
        get() = andesDatePickerAttrs.andesDatePickerMaxDate
        set(value) {
            andesDatePickerAttrs = andesDatePickerAttrs.copy(andesDatePickerMaxDate = value)
            setupComponents(createConfig())
        }

    /**
     * Getter and setter for [btnVisibility].
     */
    var btnVisibility: Boolean? = null
        get() = andesDatePickerAttrs.andesBtnVisibility
    private lateinit var andesDatePickerAttrs: AndesDatePickerAttrs

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
            context: Context,
            text: String? = DEFAULT_TEXT,
            minDate: String? = DEFAULT_MIN_DATE.toString(),
            maxDate: String? = DEFAULT_MAX_DATE.toString(),
            applyButtonVisibility: Boolean? = false
    ) : super(context) {
        initAttrs(text, minDate, maxDate, applyButtonVisibility)
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesDatePickerAttrs = AndesDatePickerAttrParser.parse(context, attrs)
        val config = AndesDatePickerConfigurationFactory.create(andesDatePickerAttrs)
        setupComponents(config)
    }

    private fun initAttrs(
            text: String?,
            minDate: String?,
            maxDate: String?,
            applyButtonVisibility: Boolean?
    ) {
        andesDatePickerAttrs = AndesDatePickerAttrs(text, minDate, maxDate, applyButtonVisibility)
        val config = AndesDatePickerConfigurationFactory.create(andesDatePickerAttrs)
        setupComponents(config)
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesDatePicker.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesDatePickerConfiguration) {
        initComponents()
        setupViewId()
        config.minDate?.toLong()?.let { setupMinDate(it) }
        config.maxDate?.toLong()?.let { setupMaxDate(it) }
        config.text.let { setupButtonText(it) }
        config.applyButtonVisibility.let { setupButtonVisibility(it) }
    }

    /**
     * Creates all the views that are part of this andesDatePicker.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        LayoutInflater.from(context).inflate(R.layout.andes_layout_datepicker, this)
        onCheckedChangeListener(andesBtnSelectDate)
        val version: Int = Build.VERSION.SDK_INT
        if (version <= API_LEVEL){
            calendarView.layoutParams.width = WIDTH
            calendarView.layoutParams.height = HEIGHT
        }
    }

    /**
     * Sets a view id to this andesDatePicker.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    private fun validateDateToCurrentDate(date: Date): Int {
        return getNow().compareTo(date)
    }

    private fun getNow(): Date {
        return convertStringToDate(SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().time), DATE_FORMAT)
    }

    private fun convertStringToDate(time: String, format: String): Date {
        val format = SimpleDateFormat(format)
        val date = format.parse(time)
        return date
    }

    fun setDateAppearance(appearance: Int) {
        calendarView.dateTextAppearance = appearance
    }

    fun setWeekDayAppearance(weekAppearance: Int) {
        calendarView.weekDayTextAppearance = weekAppearance
    }

    /**
     * Responsible for set the minimal date available.
     */

    private fun setMinDate(minDate: Long) {
        calendarView.minDate = DEFAULT_MIN_DATE
        val minDateDate: Date = Date(minDate)
        val dateDiference = validateDateToCurrentDate(minDateDate)
        var isMaxDateAfterMinDate: Boolean = minDateDate.time < calendarView.maxDate
        if (validateDateToCurrentDate(minDateDate) == 0) {
            calendarView.minDate = minDate
        } else {
            if (dateDiference < 0 && isMaxDateAfterMinDate) {
                calendarView.minDate = minDate
            }
        }
    }

    /**
     * Responsible for set the maximal date available.
     */

    private fun setMaxDate(maxDate: Long) {
        calendarView.maxDate = DEFAULT_MAX_DATE
        val maxDateDate: Date = Date(maxDate)
        val dateDiference = validateDateToCurrentDate(maxDateDate)
        var isMaxDateAfterMinDate: Boolean = maxDateDate.time > calendarView.minDate
        if (validateDateToCurrentDate(maxDateDate) == 0) {
            calendarView.maxDate = maxDate
        } else {
            if (dateDiference < 0 && isMaxDateAfterMinDate) {
                calendarView.maxDate = maxDate
                if (calendarView.minDate < 0) {
                    calendarView.minDate = 0
                }
            }
        }
    }

    fun clearMinMaxDate() {
        calendarView.minDate = DEFAULT_MIN_DATE
        calendarView.maxDate = DEFAULT_MAX_DATE
    }

    fun setupMinDate(minDate: Long) {
        setMinDate(minDate)
    }

    fun setupMaxDate(maxDate: Long) {
        setMaxDate(maxDate)
    }

    fun setupButtonText(text: String?) {
        andesBtnSelectDate.text = text
    }

    fun setupButtonVisibility(applyButtonVisibility: Boolean?) {
        if (applyButtonVisibility == true) {
            andesBtnSelectDate.visibility = View.VISIBLE
        } else {
            andesBtnSelectDate.visibility = View.GONE
        }
    }

    /**
     * Responsible for recive the selected date
     */

    private fun onCheckedChangeListener(andesBtnSelectDate: AndesButton) {
        val calendar = Calendar.getInstance()
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            if (andesBtnSelectDate.visibility == View.GONE) {
                listener?.onDateApply(calendar)
            }
        }

        andesBtnSelectDate.setOnClickListener {
            listener?.onDateApply(calendar)

        }
    }

    private fun createConfig() = AndesDatePickerConfigurationFactory.create(andesDatePickerAttrs)
    fun setDateListener(listener: ApplyDatePickerClickListener) {
        this.listener = listener
    }

    companion object {
        private const val DEFAULT_TEXT= "Aplicar"
        private const val DEFAULT_MIN_DATE = -2208973392000
        private const val DEFAULT_MAX_DATE = 4133905200000
        private const val DATE_FORMAT = "dd/MM/yyyy"
        private const val API_LEVEL = 21
        private const val HEIGHT = 900
        private const val WIDTH = 900
    }
}
