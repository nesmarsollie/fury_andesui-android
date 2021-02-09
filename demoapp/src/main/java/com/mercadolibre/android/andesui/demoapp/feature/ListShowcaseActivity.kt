package com.mercadolibre.android.andesui.demoapp.feature

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.feature.utils.PageIndicator
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.AndesListViewItemChevron
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType
import com.mercadolibre.android.andesui.radiobuttongroup.AndesRadioButtonGroup
import com.mercadolibre.android.andesui.radiobuttongroup.RadioButtonItem
import kotlinx.android.synthetic.main.andesui_list_showcase.view.*

class ListShowcaseActivity : AppCompatActivity(), AndesListDelegate {
    private var itemSelectedPosition: Int = 0
    private lateinit var andesList: AndesList
    private lateinit var adapter: AndesShowcasePagerAdapter
    private var andesListItemTitle = "Title"
    private var andesListItemSubtitle = "Subtitle"
    private var avatar: Drawable? = null
    private var icon: Drawable? = null
    private var titleNumberOfLines: Int = DEFAULT_TITLE_NUMBER_OF_LINES
    private var showItemSelections: Boolean = false

    private lateinit var buttonClear: AndesButton
    private lateinit var buttonUpdate: AndesButton
    private lateinit var sizeSpinner: Spinner
    private lateinit var itemTitle: EditText
    private lateinit var itemSubtitle: EditText
    private lateinit var itemTitleNumberOfLines: EditText
    private lateinit var andesListDivider: AndesCheckbox
    private lateinit var andesListSelection: AndesCheckbox

    companion object {
        const val LIST_SIZE = 100
        const val DEFAULT_TITLE_NUMBER_OF_LINES = 50

        const val SIMPLE = 0
        const val CHEVRON = 1
        const val CHECK_BOX = 2
        const val RADIO_BUTTON = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)
        setAdapterLogic()
        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andesui_demoapp_screen_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setAdapterLogic() {
        val viewPager = findViewById<ViewPager>(R.id.andesui_viewpager)
        viewPager.adapter = AndesShowcasePagerAdapter(this)

        adapter = viewPager.adapter as AndesShowcasePagerAdapter
        andesList = adapter.views[0].andesList
        andesList.dividerItemEnabled = true
        andesList.delegate = this

        handleListeners(adapter.views[0])
    }

    private fun handleListeners(container: View) {
        buttonClear = container.findViewById(R.id.buttonClear)
        buttonUpdate = container.findViewById(R.id.buttonUpdate)
        sizeSpinner = container.findViewById(R.id.andes_list_show_case_spinner)
        itemTitle = container.findViewById(R.id.editTextListItemTitle)
        itemSubtitle = container.findViewById(R.id.editTextListItemSubtitle)
        itemTitleNumberOfLines = container.findViewById(R.id.editTextNumberOfLines)
        andesListDivider = container.findViewById(R.id.andesListShowCaseDivider)
        andesListSelection = container.findViewById(R.id.andesListShowCaseSelection)

        sizeSpinner.setSelection(1) // medium value

        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
            ) {
                andesList.size = when (sizeSpinner.getItemAtPosition(position).toString().toLowerCase()) {
                    "small" -> {
                        AndesListViewItemSize.SMALL
                    }
                    "medium" -> {
                        AndesListViewItemSize.MEDIUM
                    }
                    "large" -> {
                        AndesListViewItemSize.LARGE
                    }
                    else -> AndesListViewItemSize.MEDIUM
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing.
            }
        }

        buttonClear.setOnClickListener {
            clear()
        }

        buttonUpdate.setOnClickListener {
            update()
        }

    }

    private fun getItemTypeSelected(): AndesListType {
        return when (adapter.andesListItemTypeSelected) {
            SIMPLE -> AndesListType.SIMPLE
            CHEVRON -> AndesListType.CHEVRON
            CHECK_BOX -> AndesListType.CHECK_BOX
            RADIO_BUTTON -> AndesListType.RADIO_BUTTON
            else -> AndesListType.SIMPLE
        }
    }

    private fun update() {
        andesListItemTitle = itemTitle.text.toString()
        andesListItemSubtitle = itemSubtitle.text.toString()
        andesList.type = getItemTypeSelected()

        showItemSelections = andesListSelection.status == AndesCheckboxStatus.SELECTED

        andesList.dividerItemEnabled = andesListDivider.status == AndesCheckboxStatus.SELECTED

        if (itemTitleNumberOfLines.text.toString().isNotEmpty()) {
            titleNumberOfLines = itemTitleNumberOfLines.text.toString().toInt()
        }

        avatar = null
        icon = null

        if (adapter.assetTypeSelected == 0) {
            icon = ContextCompat.getDrawable(this, R.drawable.andes_envio_envio_24)
        } else {
            avatar = ContextCompat.getDrawable(this, R.drawable.andes_otros_almanaque_20)
        }

        andesList.refreshListAdapter()
    }

    private fun clear() {
        titleNumberOfLines = DEFAULT_TITLE_NUMBER_OF_LINES

        andesListItemTitle = "Title"
        andesListItemSubtitle = "Subtitle"

        itemTitle.setText("Title")
        itemSubtitle.setText("Subtitle")
        itemTitleNumberOfLines.setText("")

        andesList.dividerItemEnabled = true
        showItemSelections = false

        sizeSpinner.setSelection(1)

        AndesShowcasePagerAdapter(this).clear()
        setAdapterLogic()

        avatar = null
        icon = null

        andesList.refreshListAdapter()
    }

    class AndesShowcasePagerAdapter(private val context: Context) : PagerAdapter() {
        var andesListItemTypeSelected = 0
        var assetTypeSelected = 0
        var radioButtons = arrayListOf<RadioButtonItem>()
        var radioButtons2 = arrayListOf<RadioButtonItem>()
        lateinit var radioButtonGroup: AndesRadioButtonGroup
        lateinit var radioButtonGroup2: AndesRadioButtonGroup

        var views: List<View>

        init {
            views = initViews()
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.removeAllViews()
            container.addView(views[position])
            return views[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View?)
        }

        override fun isViewFromObject(view: View, other: Any): Boolean {
            return view == other
        }

        override fun getCount(): Int = views.size

        private fun initViews(): List<View> {
            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(R.layout.andesui_list_showcase, null, false)

            val dynamicRadioButtonGroupLayout = addDynamicRadioButtonGroupLayout(layout)

            val spinnerType: Spinner = layout.findViewById(R.id.andes_list_show_case_spinner)
            ArrayAdapter.createFromResource(
                    context,
                    R.array.type_list_spinner,
                    android.R.layout.simple_spinner_item
            )
                    .also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerType.adapter = adapter
                    }

            return listOf<View>(layout, dynamicRadioButtonGroupLayout)
        }

        fun clear() {
            views = initViews()
        }

        private fun addDynamicRadioButtonGroupLayout(layout: View): View {
            radioButtons.add(RadioButtonItem(context.getString(R.string.andes_radiobutton_text_list_simple), AndesRadioButtonType.IDLE))
            radioButtons.add(RadioButtonItem(context.getString(R.string.andes_radiobutton_text_list_chevron), AndesRadioButtonType.IDLE))


            radioButtons2.add(RadioButtonItem(context.getString(R.string.andes_radiobutton_text_list_icon), AndesRadioButtonType.IDLE))
            radioButtons2.add(RadioButtonItem(context.getString(R.string.andes_radiobutton_text_list_thumbnail), AndesRadioButtonType.IDLE))

            radioButtonGroup = layout.findViewById(R.id.radioButtonGroup1)
            radioButtonGroup.selected = 0
            radioButtonGroup.radioButtons = radioButtons
            radioButtonGroup.setupCallback(
                    object : AndesRadioButtonGroup.OnRadioButtonCheckedChanged {
                        override fun onRadioButtonCheckedChanged(index: Int) {
                            andesListItemTypeSelected = index
                        }
                    }
            )

            radioButtonGroup2 = layout.findViewById(R.id.radioButtonGroup2)
            radioButtonGroup2.radioButtons = radioButtons2
            radioButtonGroup.selected = 0
            radioButtonGroup2.setupCallback(
                    object : AndesRadioButtonGroup.OnRadioButtonCheckedChanged {
                        override fun onRadioButtonCheckedChanged(index: Int) {
                            assetTypeSelected = index
                        }
                    }
            )

            return layout
        }
    }

    override fun getDataSetSize(andesList: AndesList): Int = LIST_SIZE

    override fun onItemClick(andesList: AndesList, position: Int) {

        Toast.makeText(this, "Position of item selected $position", Toast.LENGTH_SHORT).show()

        itemSelectedPosition = position

        andesList.refreshListAdapter()
    }

    override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
        val row: AndesListViewItem?
        val showItemSelected = showItemSelections && itemSelectedPosition == position

        if (andesList.type == AndesListType.SIMPLE) {
            row = AndesListViewItemSimple(
                    this,
                    andesListItemTitle,
                    subtitle = andesListItemSubtitle,
                    size = andesList.size,
                    icon = icon,
                    avatar = avatar,
                    titleMaxLines = titleNumberOfLines,
                    itemSelected = showItemSelected
            )
        } else {
            row = AndesListViewItemChevron(
                    this,
                    title = andesListItemTitle,
                    subtitle = andesListItemSubtitle,
                    size = andesList.size,
                    icon = icon,
                    avatar = avatar,
                    titleMaxLines = titleNumberOfLines,
                    itemSelected = showItemSelected
            )
        }

        return row
    }
}
