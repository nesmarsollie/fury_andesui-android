package com.mercadolibre.android.andesui.textfield

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.textfield.bahaviours.MaskBehaviour
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import junit.framework.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextfieldTest {
    private var context = RuntimeEnvironment.application
    private lateinit var textfield: AndesTextfield

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        textfield = AndesTextfield(context)
    }

    @Test
    fun `set left icon`() {
        textfield.setLeftIcon("andes_navegacion_categorias_24")
        assertEquals(textfield.leftContent, AndesTextfieldLeftContent.ICON)
    }

    @Test
    fun `set right icon`() {
        textfield.setRightIcon("andes_navegacion_categorias_24")
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ICON)
    }

    @Test
    fun `set prefix`() {
        textfield.setPrefix("prefix")
        assertEquals(textfield.leftContent, AndesTextfieldLeftContent.PREFIX)
    }

    @Test
    fun `set suffix`() {
        textfield.setSuffix("suffix")
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.SUFFIX)
    }

    @Test
    fun `set action`() {
        textfield.setAction("action", View.OnClickListener { })
        assertEquals(textfield.rightContent, AndesTextfieldRightContent.ACTION)
    }

    @Test
    fun `set mask`() {
        textfield.behaviour = MaskBehaviour("###--##,")
        assertEquals(textfield.counter, 5)
    }

    @Test
    fun `get text with mask`() {
        textfield.behaviour = MaskBehaviour("(##) ####-####")
        textfield.text = "1149778767"
        assertEquals(textfield.text, "(11) 4977-8767")
    }

    @Test
    fun `get text without maskWatcher`() {
        textfield.text = "1149778767"
        assertEquals(textfield.text, "1149778767")
    }

    @Test
    fun `textfield with watcher`() {
        val watcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        }
        textfield.textWatcher = watcher
        assertNotNull(textfield.textWatcher)
    }

    @Test
    fun `textfield without watcher`() {
        assertNull(textfield.textWatcher)
    }
}
