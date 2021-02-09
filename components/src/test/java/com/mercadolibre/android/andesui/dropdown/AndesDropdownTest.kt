package com.mercadolibre.android.andesui.dropdown

import android.os.Build
import android.view.View
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownAttrs
import com.mercadolibre.android.andesui.dropdown.factory.AndesDropdownConfigurationFactory
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemSimple
import com.mercadolibre.android.andesui.list.size.AndesListViewItemSize
import com.mercadolibre.android.andesui.list.type.AndesListType
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesDropdownTest {
    private var context = RuntimeEnvironment.application
    private lateinit var andesDropdownAttrs: AndesDropdownAttrs
    private val configFactory = Mockito.spy(AndesDropdownConfigurationFactory)

    companion object {
        private const val SIZE = 10
    }

    @Test
    fun `test delegate`() {
        val dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.SMALL)
        val list = AndesList(context, AndesListViewItemSize.MEDIUM, AndesListType.SIMPLE)
        list.delegate = object : AndesListDelegate {

            override fun onItemClick(andesList: AndesList, position: Int) {
                //Do nothing
            }

            override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
                return AndesListViewItemSimple(context, "title")
            }

            override fun getDataSetSize(andesList: AndesList): Int {
                return SIZE
            }

        }

        val listItems = ArrayList<AndesDropDownItem>()
        val item = AndesDropDownItem()

        for (i in 1..SIZE) {
            listItems.add(item)
        }

        val andesDropdownDelegate = object : AndesDropdownDelegate {
            override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                Assert.assertEquals(true, listItems[position].isSelected)
            }
        }

        dropdown.delegate = andesDropdownDelegate
        dropdown.setItems(listItems)
        dropdown.onItemClick(list, 5)
    }

    @Test
    fun `test dropdown standalone with different sizes`() {
        var dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.SMALL)
        Assert.assertEquals(dropdown.size, AndesDropdownSize.SMALL)

        dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.MEDIUM)
        Assert.assertEquals(dropdown.size, AndesDropdownSize.MEDIUM)

        dropdown = AndesDropdownStandalone(context, AndesDropdownMenuType.BOTTOMSHEET, AndesDropdownSize.LARGE)
        Assert.assertEquals(dropdown.size, AndesDropdownSize.LARGE)
    }

    @Test
    fun `test dropdown Attrs`() {
        val label = "label"
        val helper = "helper"
        val placeHolder = "placeHolder"

        andesDropdownAttrs = AndesDropdownAttrs(
                andesDropdownMenuType = AndesDropdownMenuType.BOTTOMSHEET,
                andesDropdownLabel = label,
                andesDropdownHelper = helper,
                andesDropdownPlaceHolder = placeHolder)

        val config = configFactory.create(andesDropdownAttrs)

        Assert.assertEquals(config.menuType, AndesDropdownMenuType.BOTTOMSHEET)
        Assert.assertEquals(config.label, label)
        Assert.assertEquals(config.helper, helper)
        Assert.assertEquals(config.placeHolder, placeHolder)
    }

}
