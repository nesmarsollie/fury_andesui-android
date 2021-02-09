package com.mercadolibre.android.andesui.dropdown

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrParser
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrs
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfiguration
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfigurationFactory
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.dropdown.utils.DropdownBottomSheetDialog
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.textfield.AndesTextfield

@SuppressWarnings("TooManyFunctions")
class AndesDropDownForm : ConstraintLayout, AndesListDelegate {
    private val bottomSheetDialog = DropdownBottomSheetDialog(context, R.style.Andes_BottomSheetDialog, this)
    private lateinit var andesDropdownDelegate: AndesDropdownDelegate
    private lateinit var andesDropdownAttrs: AndesDropdownAttrs
    private lateinit var andesTextfield: AndesTextfield
    private var listItems: List<AndesDropDownItem> = listOf()

    /**
     * Getter and setter for [label].
     */
    var label: String?
        get() = andesDropdownAttrs.andesDropdownLabel
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownLabel = value)
            setupLabelComponent(createConfig())
        }

    /**
     * Getter and setter for [helper].
     */
    var helper: String?
        get() = andesDropdownAttrs.andesDropdownHelper
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownHelper = value)
            setupHelperComponent(createConfig())
        }

    /**
     * Getter and setter for [placeholder].
     */
    var placeholder: String?
        get() = andesDropdownAttrs.andesDropdownPlaceHolder
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownPlaceHolder = value)
            setupPlaceHolderComponent(createConfig())
        }

    /**
     * Getter and setter for [delegate].
     */
    var delegate: AndesDropdownDelegate
        get() = andesDropdownDelegate
        set(value) {
            andesDropdownDelegate = value
        }

    /**
     * Getter and setter for [menuType].
     */
    var menuType: AndesDropdownMenuType
        get() = andesDropdownAttrs.andesDropdownMenuType
        set(value) {
            andesDropdownAttrs = andesDropdownAttrs.copy(andesDropdownMenuType = value)
            setupMenuType(createConfig())
        }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
            context: Context,
            menuType: AndesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
            label: String,
            helper: String,
            placeHolder: String
    ) : super(context) {
        initAttrs(menuType, label, helper, placeHolder)
    }

    private fun setupMenuType(config: AndesDropdownConfiguration) {
        if (config.menuType == AndesDropdownMenuType.BOTTOMSHEET) {
            setupBottomSheet()
        } else {
            Log.d("AndesDropDownForm", "Menu selected is no developed yet")
        }
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesDropdownAttrs = AndesDropdownAttrParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
            menuType: AndesDropdownMenuType,
            label: String,
            helper: String,
            placeHolder: String
    ) {
        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = menuType,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder)
        setupComponents(createConfig())
    }

    /**
     * Responsible for setting up all properties of each component that is part of this andesDropdown.
     * Is like a choreographer 😉
     */
    private fun setupComponents(config: AndesDropdownConfiguration) {
        initComponents()
        setupViewId()
        setupAndesTextFieldComponent(config)
        setupMenuType(config)
    }

    /**
     * Sets a view id to this dropdown.
     */
    private fun setupViewId() {
        if (id == NO_ID) { // If this view has no id
            id = View.generateViewId()
        }
    }

    /**
     * Creates all the views that are part of this Dropdown.
     * After a view is created then a view id is added to it.
     */
    private fun initComponents() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_dropdown_form, this)
        andesTextfield = container.findViewById(R.id.andes_text_field_dropdown_form)
    }

    private fun setupBottomSheet() {
        bottomSheetDialog.setOnShowListener {
            bottomSheetDialog.andesList?.refreshListAdapter()

            setChevronIcon(ICON_CHEVRON_UP)
        }

        bottomSheetDialog.setOnDismissListener {
            setChevronIcon(ICON_CHEVRON_DOWN)
            andesTextfield.clearFocus()
        }

    }

    private val onFocusChange = OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            openBottomSheet()
        }
    }

    /**
     * Sets the list of item that the Dropdown will draw
     */
    fun setItems(listItems: List<AndesDropDownItem>){
        this.listItems = listItems
    }

    private fun openBottomSheet() {
        bottomSheetDialog.show()
    }

    /**
     * Gets data from the config and sets to the AndesTextField Helper component.
     */
    private fun setupHelperComponent(config: AndesDropdownConfiguration) {
        andesTextfield.helper = config.helper
    }

    /**
     * Gets data from the config and sets to the AndesTextField PlaceHolder component.
     */
    private fun setupPlaceHolderComponent(config: AndesDropdownConfiguration) {
        andesTextfield.placeholder = config.placeHolder
    }

    /**
     * Gets data from the config and sets to the AndesTextField Label component.
     */
    private fun setupLabelComponent(config: AndesDropdownConfiguration) {
        andesTextfield.label = config.label
    }

    private fun setChevronIcon(chevronIcon: String) {
        andesTextfield.setRightIcon(
                chevronIcon,
                null,
                R.color.andes_blue_ml_500)
    }

    private fun setupAndesTextFieldComponent(config: AndesDropdownConfiguration) {
        setChevronIcon(ICON_CHEVRON_DOWN)

        andesTextfield.onFocusChangeListener = onFocusChange
        andesTextfield.isEnabled = false
        andesTextfield.inputType = InputType.TYPE_NULL
    }

    override fun onItemClick(andesList: AndesList, position: Int) {
        selectItem(position)

        andesList.refreshListAdapter()

        delegate.onItemSelected(this, position)

        bottomSheetDialog.dismiss()
    }

    private fun selectItem(position: Int){
        val itemSelected = listItems[position]

        listItems.forEach {
            it.isSelected = itemSelected == it
        }

        andesTextfield.text = itemSelected.title
    }

    override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
        val item = listItems[position]
        val row: AndesListViewItem?

        row = AndesListViewItemSimple(
                context,
                item.title,
                size = andesList.size,
                avatar = item.avatar,
                itemSelected = item.isSelected
        )

        return row
    }

    override fun getDataSetSize(andesList: AndesList): Int = listItems.size

    private fun createConfig() = AndesDropdownConfigurationFactory.create(andesDropdownAttrs)

    companion object {
        private const val ICON_CHEVRON_DOWN: String = "andes_ui_chevron_down_24"
        private const val ICON_CHEVRON_UP: String = "andes_ui_chevron_up_24"
    }

}
