package com.mercadolibre.android.andesui.message

import android.graphics.drawable.Drawable
import android.os.Build
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.createBackgroundColorConfigLoud
import com.mercadolibre.android.andesui.button.hierarchy.createBackgroundColorConfigTransparent
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.message.factory.AndesMessageAttrs
import com.mercadolibre.android.andesui.message.factory.AndesMessageConfigurationFactory
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchy
import com.mercadolibre.android.andesui.message.type.AndesMessageType
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMessageConfigurationQuiet {
    private var context = RuntimeEnvironment.application

    private lateinit var andesMessage: AndesMessage
    private val configFactory = spy(AndesMessageConfigurationFactory)
    private lateinit var attrs: AndesMessageAttrs

    @Test
    fun `Quiet, Neutral background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_gray_040.toAndesColor())
    }

    @Test
    fun `background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_gray_040.toAndesColor())
    }

    @Test
    fun `Quiet, Success background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_gray_040.toAndesColor())
    }

    @Test
    fun `Quiet, Error background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_gray_040.toAndesColor())
    }

    @Test
    fun `Quiet, Warning background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_gray_040.toAndesColor())
    }

    @Test
    fun `Quiet, Neutral icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Success icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_green_500.toAndesColor())
    }

    @Test
    fun `Quiet, Error icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_red_500.toAndesColor())
    }

    @Test
    fun `Quiet, Warning icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_orange_500.toAndesColor())
    }

    @Test
    fun `Quiet, Neutral pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Success pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_green_500.toAndesColor())
    }

    @Test
    fun `Quiet, Error pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_red_500.toAndesColor())
    }

    @Test
    fun `Quiet, Warning pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_orange_500.toAndesColor())
    }

    @Test
    fun `Quiet, Neutral text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_gray_800.toAndesColor())
    }

    @Test
    fun `Quiet, Success text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_gray_800.toAndesColor())
    }

    @Test
    fun `Quiet, Error text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_gray_800.toAndesColor())
    }

    @Test
    fun `Quiet, Warning text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_gray_800.toAndesColor())
    }

    @Test
    fun `Quiet, Neutral title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Quiet, Success title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Quiet, Error title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Quiet, Warning title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Quiet, Neutral body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Quiet, Success body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Quiet, Error body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Quiet, Warning body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Quiet, Neutral null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Quiet, Success null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Quiet, Error null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Quiet, Warning null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Quiet, Neutral title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Quiet, Success title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Quiet, Error title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Quiet, Warning title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Quiet, Neutral body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Quiet, Success body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Quiet, Error body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body", "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Quiet, Warning body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Quiet, Neutral primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionBackgroundColor, createBackgroundColorConfigLoud())
    }

    @Test
    fun `Quiet, Success primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionBackgroundColor, createBackgroundColorConfigLoud())
    }

    @Test
    fun `Quiet, Error primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionBackgroundColor, createBackgroundColorConfigLoud())
    }

    @Test
    fun `Quiet, Warning primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionBackgroundColor, createBackgroundColorConfigLoud())
    }

    @Test
    fun `Quiet, Neutral secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, createBackgroundColorConfigTransparent())
    }

    @Test
    fun `Quiet, Success secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, createBackgroundColorConfigTransparent())
    }

    @Test
    fun `Quiet, Error secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, createBackgroundColorConfigTransparent())
    }

    @Test
    fun `Quiet, Warning secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, createBackgroundColorConfigTransparent())
    }

    @Test
    fun `Quiet, Neutral primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Quiet, Success primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body", "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Quiet, Error primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Quiet, Warning primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Quiet, Neutral secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Success secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Error secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Warning secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }
    @Test
    fun `Quiet, Neutral link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
            "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Neutral with Thumbnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.NEUTRAL, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }

    @Test
    fun `Quiet, Success link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Error link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
            "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }


    @Test
    fun `Quiet, Warning link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Quiet, Success with thumbnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.SUCCESS, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }

    @Test
    fun `Quiet, Error with thumbnnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.ERROR, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }

    @Test
    fun `Quiet, Warning with thumbnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.QUIET, AndesMessageType.WARNING, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }
}
