package com.mercadolibre.android.andesui.message

import android.graphics.drawable.Drawable
import android.os.Build
import com.mercadolibre.android.andesui.BuildConfig
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.BackgroundColorConfig
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
class AndesMessageConfigurationLoudTest {

    private var context = RuntimeEnvironment.application

    private lateinit var andesMessage: AndesMessage
    private val configFactory = spy(AndesMessageConfigurationFactory)
    private lateinit var attrs: AndesMessageAttrs

    @Test
    fun `Loud, Neutral background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Loud, Success background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_green_500.toAndesColor())
    }

    @Test
    fun `Loud, Error background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_red_500.toAndesColor())
    }

    @Test
    fun `Loud, Warning background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.backgroundColor, R.color.andes_orange_500.toAndesColor())
    }

    @Test
    fun `Loud, Neutral icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Loud, Success icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_green_600.toAndesColor())
    }

    @Test
    fun `Loud, Error icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_red_600.toAndesColor())
    }

    @Test
    fun `Loud, Warning icon background color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.iconBackgroundColor, R.color.andes_orange_600.toAndesColor())
    }

    @Test
    fun `Loud, Neutral pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_accent_color_500.toAndesColor())
    }

    @Test
    fun `Loud, Success pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_green_500.toAndesColor())
    }

    @Test
    fun `Loud, Error pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_red_500.toAndesColor())
    }

    @Test
    fun `Loud, Warning pipe color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.pipeColor, R.color.andes_orange_500.toAndesColor())
    }

    @Test
    fun `Loud, Neutral text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Success text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Error text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Warning text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.textColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Neutral title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Loud, Success title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Loud, Error title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Loud, Warning title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, "Title")
    }

    @Test
    fun `Loud, Neutral body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Loud, Success body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Loud, Error body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Loud, Warning body text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodyText, "Body")
    }

    @Test
    fun `Loud, Neutral null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Loud, Success null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Loud, Error null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Loud, Warning null title text`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                null, true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleText, null)
    }

    @Test
    fun `Loud, Neutral title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Loud, Success title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Loud, Error title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Loud, Warning title size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.titleSize, context.resources.getDimension(R.dimen.andes_message_title))
    }

    @Test
    fun `Loud, Neutral body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Loud, Success body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Loud, Error body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Loud, Warning body size`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.bodySize, context.resources.getDimension(R.dimen.andes_message_body))
    }

    @Test
    fun `Loud, Neutral primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        val andesColor = R.color.andes_accent_color_600.toAndesColor()
        assertEquals(config.primaryActionBackgroundColor,
                BackgroundColorConfig(enabledColor = R.color.andes_accent_color_600.toAndesColor(),
                pressedColor = R.color.andes_accent_color_800.toAndesColor(),
                focusedColor = R.color.andes_accent_color_400.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Success primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        val andesColor = R.color.andes_green_600.toAndesColor()
        assertEquals(config.primaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_green_600.toAndesColor(),
                pressedColor = R.color.andes_green_800.toAndesColor(),
                focusedColor = R.color.andes_green_400.toAndesColor(),
                hoveredColor = R.color.andes_green_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Error primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        val andesColor = R.color.andes_red_600.toAndesColor()
        assertEquals(config.primaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_red_600.toAndesColor(),
                pressedColor = R.color.andes_red_800.toAndesColor(),
                focusedColor = R.color.andes_red_400.toAndesColor(),
                hoveredColor = R.color.andes_red_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Warning primary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        val andesColor = R.color.andes_orange_600.toAndesColor()
        assertEquals(config.primaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_orange_600.toAndesColor(),
                pressedColor = R.color.andes_orange_800.toAndesColor(),
                focusedColor = R.color.andes_orange_400.toAndesColor(),
                hoveredColor = R.color.andes_orange_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Neutral secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_accent_color_500.toAndesColor(),
                pressedColor = R.color.andes_accent_color_700.toAndesColor(),
                focusedColor = R.color.andes_accent_color_300.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Success secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_green_500.toAndesColor(),
                pressedColor = R.color.andes_green_700.toAndesColor(),
                focusedColor = R.color.andes_green_300.toAndesColor(),
                hoveredColor = R.color.andes_green_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Error secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_red_500.toAndesColor(),
                pressedColor = R.color.andes_red_700.toAndesColor(),
                focusedColor = R.color.andes_red_300.toAndesColor(),
                hoveredColor = R.color.andes_red_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Warning secondary action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_orange_500.toAndesColor(),
                pressedColor = R.color.andes_orange_700.toAndesColor(),
                focusedColor = R.color.andes_orange_300.toAndesColor(),
                hoveredColor = R.color.andes_orange_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Neutral link action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Success link action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Error link action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Warning link action color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionBackgroundColor, BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null))
    }

    @Test
    fun `Loud, Neutral primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Success primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body", "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Error primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Warning primary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.primaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Neutral secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Success secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Error secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Warning secondary action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.secondaryActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Neutral link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.NEUTRAL, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Success link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_white.toAndesColor())
    }



    @Test
    fun `Loud, Error link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
                "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Warning link action text color`() {
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
            "Title", true, null, null)
        val config = configFactory.create(context, attrs)
        assertEquals(config.linkActionTextColor, R.color.andes_white.toAndesColor())
    }

    @Test
    fun `Loud, Success with Thumbnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.SUCCESS, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }

    @Test
    fun `Loud, Error with Thumbnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.ERROR, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }

    @Test
    fun `Loud, Warning with thumbnail`() {
        val thumbnail = Mockito.mock(Drawable::class.java)
        attrs = AndesMessageAttrs(AndesMessageHierarchy.LOUD, AndesMessageType.WARNING, "Body",
            "Title", true, null, thumbnail)
        val config = configFactory.create(context, attrs)
        assertEquals(config.thumbnail, thumbnail)
    }
}
