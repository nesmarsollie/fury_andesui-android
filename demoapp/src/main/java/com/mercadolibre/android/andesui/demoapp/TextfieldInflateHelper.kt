package com.mercadolibre.android.andesui.demoapp

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.textfield.AndesTextarea
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.AndesTextfieldCode
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldCodeState
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.textfield.style.AndesTextfieldCodeStyle
import java.util.*
import kotlin.collections.ArrayList

object InflateTextfieldHelper {

    fun inflateAndesTextfield(context: Context): View {

        val layoutTextfield = LayoutInflater
                .from(context)
                .inflate(R.layout.andesui_textfield_showcase_change,
                        null,
                        false
                )

        val textfield = layoutTextfield.findViewById<AndesTextfield>(R.id.andesui_textfield)
        val button = layoutTextfield.findViewById<AndesButton>(R.id.change_button)
        val clearButton = layoutTextfield.findViewById<AndesButton>(R.id.clear_button)
        val label = layoutTextfield.findViewById<AndesTextfield>(R.id.label_text)
        val helper = layoutTextfield.findViewById<AndesTextfield>(R.id.helper_text)
        val placeholder = layoutTextfield.findViewById<AndesTextfield>(R.id.placeholder_text)
        val counter = layoutTextfield.findViewById<EditText>(R.id.counter)
        val mask = layoutTextfield.findViewById<AndesTextfield>(R.id.mask)

        counter.setText(COUNTER_DEFAULT_TEXT)
        textfield.counter = COUNTER_DEFAULT

        val inputTypeSpinner: Spinner = layoutTextfield.findViewById(R.id.textType_spinner)
        val typeAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, getInputTypesArray())
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputTypeSpinner.adapter = typeAdapter

        val stateSpinner: Spinner = layoutTextfield.findViewById(R.id.state_spinner)
        val stateAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.textfield_state_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        val preffixSpinner: Spinner = layoutTextfield.findViewById(R.id.prefix_spinner)
        val preffixAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.prefix_spinner)
        )
        preffixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        preffixSpinner.adapter = preffixAdapter

        val suffixSpinner: Spinner = layoutTextfield.findViewById(R.id.suffix_spinner)
        val suffixAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.suffix_spinner)
        )
        suffixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        suffixSpinner.adapter = suffixAdapter

        button.setOnClickListener {
            textfield.text = ""
            textfield.label = label.text.toString()
            textfield.helper = helper.text.toString()
            textfield.placeholder = placeholder.text.toString()

            textfield.counter = counter.text.toString().toIntOrNull() ?: 0

            textfield.state = AndesTextfieldState.valueOf(stateSpinner.selectedItem.toString().toUpperCase())

            if (preffixSpinner.selectedItem.toString().toUpperCase() == NONE) {
                textfield.leftContent = null
            } else {
                textfield.leftContent = AndesTextfieldLeftContent.fromString(preffixSpinner.selectedItem.toString())
            }

            if (suffixSpinner.selectedItem.toString().toUpperCase() == NONE) {
                textfield.rightContent = null
            } else {
                textfield.rightContent = AndesTextfieldRightContent.fromString(
                        suffixSpinner.selectedItem.toString()
                )
                if (textfield.rightContent == AndesTextfieldRightContent.ACTION) {
                    textfield.setAction(
                            "Button",
                            View.OnClickListener {
                                Toast.makeText(context, "Right action pressed", Toast.LENGTH_LONG).show()
                            }
                    )
                }
            }

            mask.text.takeIf { !it.isNullOrEmpty() }?.apply {
                textfield.setTextFieldMask(it.toString())
            }

            if (!mask.text.isNullOrEmpty()) {
                textfield.setTextFieldMask(mask.text.toString())
            }

            val selectedInputType = getInputTypesArray().single {
                it.name == inputTypeSpinner.selectedItem.toString()
            }.value
            textfield.inputType = selectedInputType
        }

        clearButton.setOnClickListener {
            // reset UI
            label.text = null
            placeholder.placeholder = context.resources.getString(R.string.andes_textfield_placeholder_text)
            placeholder.text = null
            helper.text = null
            counter.setText(COUNTER_DEFAULT_TEXT)
            mask.text = ""
            stateSpinner.setSelection(0)
            inputTypeSpinner.setSelection(0)
            preffixSpinner.setSelection(0)
            suffixSpinner.setSelection(0)

            // reset AndesTextfield's properties.
            textfield.text = ""
            textfield.label = null
            textfield.placeholder = null
            textfield.helper = null
            textfield.counter = COUNTER_DEFAULT
            textfield.state = AndesTextfieldState.IDLE
            textfield.inputType = InputType.TYPE_CLASS_DATETIME
            textfield.leftContent = null
            textfield.rightContent = null
            textfield.clearMask()
        }

        return layoutTextfield
    }

    fun inflateAndesTextfieldArea(context: Context): View {
        val layoutTextfieldArea = LayoutInflater
                .from(context)
                .inflate(R.layout.andesui_textarea_showcase_change, null, false)
        val textarea = layoutTextfieldArea.findViewById<AndesTextarea>(R.id.andesui_tag)
        val button = layoutTextfieldArea.findViewById<AndesButton>(R.id.change_button)
        val clearButton = layoutTextfieldArea.findViewById<AndesButton>(R.id.clear_button)
        val label = layoutTextfieldArea.findViewById<AndesTextfield>(R.id.label_text)
        val helper = layoutTextfieldArea.findViewById<AndesTextfield>(R.id.helper_text)
        val placeholder = layoutTextfieldArea.findViewById<AndesTextfield>(R.id.placeholder_text)
        val counter = layoutTextfieldArea.findViewById<EditText>(R.id.counter)
        counter.setText(COUNTER_DEFAULT_TEXT)
        textarea.counter = COUNTER_DEFAULT

        val stateSpinner: Spinner = layoutTextfieldArea.findViewById(R.id.state_spinner)
        val stateAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.textfield_state_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        button.setOnClickListener {
            textarea.text = ""
            textarea.label = label.text.toString()
            textarea.helper = helper.text.toString()
            textarea.placeholder = placeholder.text.toString()
            textarea.counter = counter.text.toString().toIntOrNull() ?: 0
            textarea.state = AndesTextfieldState.valueOf(stateSpinner.selectedItem.toString().toUpperCase())
        }

        clearButton.setOnClickListener {
            // reset UI
            label.text = null
            placeholder.placeholder = context.resources.getString(R.string.andes_textfield_placeholder_text)
            placeholder.text = null
            helper.text = null
            counter.setText(COUNTER_DEFAULT_TEXT)
            stateSpinner.setSelection(0)

            // reset AndesTextfield's properties.
            textarea.text = ""
            textarea.label = null
            textarea.placeholder = null
            textarea.helper = null
            textarea.counter = COUNTER_DEFAULT
            textarea.state = AndesTextfieldState.IDLE
        }

        return layoutTextfieldArea
    }

    fun inflateAndesTextfieldCode(context: Context): View {
        val layoutTextfieldCode = LayoutInflater.from(context).inflate(
                R.layout.andesui_textfield_code_showcase,
                null,
                false
        ) as ScrollView

        val textfieldCode = layoutTextfieldCode.findViewById<AndesTextfieldCode>(R.id.andesui_textfield_code)
        val updateButton = layoutTextfieldCode.findViewById<AndesButton>(R.id.change_button)
        val clearButton = layoutTextfieldCode.findViewById<AndesButton>(R.id.clear_button)
        val stateSpinner = layoutTextfieldCode.findViewById<Spinner>(R.id.state_spinner)
        val styleSpinner = layoutTextfieldCode.findViewById<Spinner>(R.id.style_spinner)
        val text = layoutTextfieldCode.findViewById<AndesTextfield>(R.id.text)
        val label = layoutTextfieldCode.findViewById<AndesTextfield>(R.id.label_text)
        val helper = layoutTextfieldCode.findViewById<AndesTextfield>(R.id.helper_text)

        configureStateAdapter(stateSpinner)
        configureStyleAdapter(styleSpinner)
        addAndesTextfieldCodeListener(textfieldCode)

        updateButton.setOnClickListener {
            val currentState = textfieldCode.state
            val newState = AndesTextfieldCodeState.valueOf(stateSpinner.selectedItem.toString().toUpperCase(Locale.getDefault()))
            if (currentState != newState) {
                textfieldCode.state = newState
            }
            val currentStyle = textfieldCode.style
            val newStyle = AndesTextfieldCodeStyle.valueOf(styleSpinner.selectedItem.toString().toUpperCase(Locale.getDefault()))
            if (currentStyle != newStyle) {
                textfieldCode.style = newStyle
            }

            textfieldCode.text = text.text
            textfieldCode.label = label.text
            textfieldCode.helper = helper.text
        }

        clearButton.setOnClickListener {
            // reset UI
            text.text = null
            label.text = null
            helper.text = null
            stateSpinner.setSelection(0)
            styleSpinner.setSelection(0)

            // reset AndesTextfieldCode's properties.
            textfieldCode.text = ""
            textfieldCode.label = null
            textfieldCode.helper = null
            textfieldCode.state = AndesTextfieldCodeState.IDLE
            textfieldCode.style = AndesTextfieldCodeStyle.THREESOME
        }

        return layoutTextfieldCode
    }

    fun inflateStaticTextfieldLayout(context: Context): View {
        val layoutTextfield = LayoutInflater.from(context).inflate(
                R.layout.andesui_textfield_showcase,
                null,
                false
        ) as ScrollView
        layoutTextfield.left

        // Set action clear
        val textfield1 = layoutTextfield.findViewById<AndesTextfield>(R.id.andesTextfield1)
        textfield1.text = context.resources.getString(R.string.andesui_demoapp_textfield_placeholder)
        textfield1.textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Toast.makeText(context, "Text changed: $s", Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no-op
            }

            override fun afterTextChanged(s: Editable) {
                // no-op
            }
        }

        // Set text
        val textfield2 = layoutTextfield.findViewById<AndesTextfield>(R.id.andesTextfield2)
        textfield2.text = context.resources.getString(R.string.andesui_demoapp_textfield_placeholder)

        // Set action
        val textfield3 = layoutTextfield.findViewById<AndesTextfield>(R.id.andesTextfield3)
        textfield3.setAction(
                "Button",
                View.OnClickListener {
                    Toast.makeText(context, "Action pressed", Toast.LENGTH_LONG).show()
                }
        )

        // Set text
        val textfield4 = layoutTextfield.findViewById<AndesTextfield>(R.id.andesTextfield4)
        textfield4.text = context.resources.getString(R.string.andesui_demoapp_textfield_placeholder)

        // Set left icon
        val textViewLeftIcon = layoutTextfield.findViewById<AndesTextfield>(R.id.textViewLeftIcon)
        textViewLeftIcon.setLeftIcon("andes_navegacion_buscar_24")

        ContextCompat.getDrawable(context, com.mercadolibre.android.andesui.R.drawable.andes_navegacion_ajustes)

        return layoutTextfield
    }

    private fun configureStateAdapter(stateSpinner: Spinner) {
        val stateAdapter = ArrayAdapter(
                stateSpinner.context,
                android.R.layout.simple_spinner_item,
                stateSpinner.resources.getStringArray(R.array.textfield_code_state_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter
    }

    private fun configureStyleAdapter(styleSpinner: Spinner) {
        val styleAdapter = ArrayAdapter(
                styleSpinner.context,
                android.R.layout.simple_spinner_item,
                styleSpinner.resources.getStringArray(R.array.textfield_code_style_spinner)
        )
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        styleSpinner.adapter = styleAdapter
    }

    private fun addAndesTextfieldCodeListener(textfieldCode: AndesTextfieldCode) {
        textfieldCode.setOnTextChangeListener(object : AndesTextfieldCode.OnTextChangeListener {
            override fun onChange(text: String) {
                Log.i("ANDES", "TEXT CHANGE: $text")
            }
        })

        textfieldCode.setOnCompleteListener(object : AndesTextfieldCode.OnCompletionListener {
            override fun onComplete(isFull: Boolean) {
                if (isFull) {
                    Log.i("ANDES", "TEXT COMPLETE: ${textfieldCode.text}")
                }
            }
        })
    }

    @Suppress("LongMethod")
    private fun getInputTypesArray(): ArrayList<InputTypeItem> {
        val inputTypes = ArrayList<InputTypeItem>()

        inputTypes.add(
                InputTypeItem("date", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE)
        )
        inputTypes.add(
                InputTypeItem("datetime", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_NORMAL)
        )
        inputTypes.add(InputTypeItem("none", InputType.TYPE_NULL))
        inputTypes.add(
                InputTypeItem("number", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        )
        inputTypes.add(
                InputTypeItem("numberDecimal", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        )
        inputTypes.add(
                InputTypeItem("numberPassword", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)
        )
        inputTypes.add(
                InputTypeItem("numberSigned", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED)
        )
        inputTypes.add(InputTypeItem("phone", InputType.TYPE_CLASS_PHONE))
        inputTypes.add(InputTypeItem("text", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL))
        inputTypes.add(InputTypeItem("textAutoComplete", InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE))
        inputTypes.add(InputTypeItem("textAutoCorrect", InputType.TYPE_TEXT_FLAG_AUTO_CORRECT))
        inputTypes.add(InputTypeItem("textCapCharacters", InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS))
        inputTypes.add(InputTypeItem("textCapSentences", InputType.TYPE_TEXT_FLAG_CAP_SENTENCES))
        inputTypes.add(InputTypeItem("textCapWords", InputType.TYPE_TEXT_FLAG_CAP_WORDS))
        inputTypes.add(
                InputTypeItem(
                        "textEmailAddress",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textEmailSubject",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
                )
        )
        inputTypes.add(InputTypeItem("textFilter", InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE))
        inputTypes.add(
                InputTypeItem(
                        "textLongMessage",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
                )
        )
        inputTypes.add(InputTypeItem("textMultiLine", InputType.TYPE_TEXT_FLAG_MULTI_LINE))
        inputTypes.add(InputTypeItem("textNoSuggestions", InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS))
        inputTypes.add(
                InputTypeItem("textPassword", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        )
        inputTypes.add(
                InputTypeItem("textPersonName", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        )
        inputTypes.add(
                InputTypeItem("textPhonetic", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PHONETIC)
        )
        inputTypes.add(
                InputTypeItem(
                        "textPostalAddress",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textShortMessage",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE
                )
        )
        inputTypes.add(InputTypeItem("textUri", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI))
        inputTypes.add(
                InputTypeItem(
                        "textVisiblePassword",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textWebEditText",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textWebEmailAddress",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                )
        )
        inputTypes.add(
                InputTypeItem(
                        "textWebPassword",
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
                )
        )
        inputTypes.add(
                InputTypeItem("time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME)
        )

        return inputTypes
    }

    internal class InputTypeItem(val name: String, val value: Int) {
        override fun toString(): String {
            return this.name
        }
    }

    private const val NONE = "NONE"
    private const val COUNTER_DEFAULT_TEXT = "50"
    private const val COUNTER_DEFAULT = 50
}