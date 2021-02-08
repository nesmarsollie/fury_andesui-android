package com.mercadolibre.android.andesui.message.factory

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.BackgroundColorConfig
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchyInterface
import com.mercadolibre.android.andesui.message.type.AndesMessageTypeInterface

internal data class AndesMessageConfiguration(
    val iconBackgroundColor: AndesColor,
    val backgroundColor: AndesColor,
    val pipeColor: AndesColor,
    val textColor: AndesColor,
    val titleText: String? = null,
    val bodyText: String? = null,
    val titleSize: Float,
    val bodySize: Float,
    val lineHeight: Int,
    val titleTypeface: Typeface?,
    val bodyTypeface: Typeface?,
    val icon: Drawable?,
    val isDismissable: Boolean,
    val dismissableIcon: Drawable?,
    val dismissableIconColor: AndesColor?,
    val primaryActionText: String?,
    val secondaryActionText: String?,
    val linkActionText: String?,
    val primaryActionBackgroundColor: BackgroundColorConfig,
    val primaryActionTextColor: AndesColor,
    val secondaryActionBackgroundColor: BackgroundColorConfig,
    val secondaryActionTextColor: AndesColor,
    val linkActionBackgroundColor: BackgroundColorConfig,
    val linkActionTextColor: AndesColor,
    val bodyLinkIsUnderline: Boolean,
    val bodyLinkTextColor: AndesColor,
    val thumbnail: Drawable?
)

@Suppress("TooManyFunctions")
internal object AndesMessageConfigurationFactory {

    fun create(context: Context, andesMessageAttrs: AndesMessageAttrs): AndesMessageConfiguration {
        return with(andesMessageAttrs) {
            AndesMessageConfiguration(
                iconBackgroundColor = resolveIconBackgroundColor(
                    andesMessageType.type,
                    andesMessageHierarchy.hierarchy
                ),
                backgroundColor = resolveBackgroundColor(andesMessageHierarchy.hierarchy, andesMessageType.type),
                pipeColor = resolvePipeColor(andesMessageType.type),
                textColor = resolveTextColor(andesMessageHierarchy.hierarchy),
                titleText = title,
                bodyText = body,
                titleSize = resolveTitleSize(context),
                lineHeight = resolveLineHeight(context),
                bodySize = resolveBodySize(context),
                titleTypeface = resolveTitleTypeface(andesMessageHierarchy.hierarchy, context),
                bodyTypeface = resolveBodyTypeface(andesMessageHierarchy.hierarchy, context),
                icon = resolveIcon(andesMessageType.type, andesMessageHierarchy.hierarchy, context),
                isDismissable = isDismissable,
                dismissableIcon = resolveDismissableIcon(andesMessageHierarchy.hierarchy, context),
                dismissableIconColor = resolveDismissableIconColor(andesMessageHierarchy.hierarchy),
                primaryActionText = null,
                secondaryActionText = null,
                linkActionText = null,

                primaryActionBackgroundColor = resolvePrimaryActionBackgroundColor(
                    andesMessageHierarchy.hierarchy,
                    andesMessageType.type
                ),
                primaryActionTextColor = resolvePrimaryActionTextColor(andesMessageHierarchy.hierarchy),

                secondaryActionBackgroundColor = resolveSecondaryActionBackgroundColor(
                    andesMessageHierarchy.hierarchy,
                    andesMessageType.type
                ),
                secondaryActionTextColor = resolveSecondaryActionTextColor(
                    andesMessageHierarchy.hierarchy,
                    andesMessageType.type
                ),
                linkActionBackgroundColor = resolveLinkActionBackgroundColor(
                    andesMessageHierarchy.hierarchy,
                    andesMessageType.type
                ),
                linkActionTextColor = resolveLinkActionTextColor(
                    andesMessageHierarchy.hierarchy,
                    andesMessageType.type
                ),

                bodyLinkIsUnderline = resolveBodyLinkIsUnderline(andesMessageHierarchy.hierarchy, andesMessageType.type),
                bodyLinkTextColor = resolveBodyLinkTextColor(andesMessageHierarchy.hierarchy, andesMessageType.type),
                thumbnail = thumbnail
            )
        }
    }

    private fun resolveIconBackgroundColor(type: AndesMessageTypeInterface, hierarchy: AndesMessageHierarchyInterface): AndesColor {
        return hierarchy.iconBackgroundColor(type)
    }
    private fun resolveBackgroundColor(hierarchy: AndesMessageHierarchyInterface, type: AndesMessageTypeInterface) = hierarchy.backgroundColor(type)
    private fun resolvePipeColor(type: AndesMessageTypeInterface) = type.pipeColor()
    private fun resolveTextColor(hierarchy: AndesMessageHierarchyInterface) = hierarchy.textColor()
    private fun resolveTitleSize(context: Context) = context.resources.getDimension(R.dimen.andes_message_title)
    private fun resolveBodySize(context: Context) = context.resources.getDimension(R.dimen.andes_message_body)
    private fun resolveLineHeight(context: Context) = context.resources.getDimension(R.dimen.andes_message_line_height).toInt()
    private fun resolveTitleTypeface(hierarchy: AndesMessageHierarchyInterface, context: Context) = hierarchy.titleTypeface(context)
    private fun resolveBodyTypeface(hierarchy: AndesMessageHierarchyInterface, context: Context) = hierarchy.bodyTypeface(context)
    private fun resolveIcon(
        type: AndesMessageTypeInterface,
        hierarchy: AndesMessageHierarchyInterface,
        context: Context
    ) = type.icon(context, hierarchy)

    private fun resolveDismissableIcon(hierarchy: AndesMessageHierarchyInterface, context: Context) = hierarchy.dismissableIcon(hierarchy, context)
    private fun resolveDismissableIconColor(hierarchy: AndesMessageHierarchyInterface) = hierarchy.dismissableIconColor()
    private fun resolvePrimaryActionBackgroundColor(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.primaryActionBackgroundColor(type)

    private fun resolvePrimaryActionTextColor(hierarchy: AndesMessageHierarchyInterface) = hierarchy.primaryActionTextColor()
    private fun resolveSecondaryActionBackgroundColor(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.secondaryActionBackgroundColor(type)

    private fun resolveSecondaryActionTextColor(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.secondaryActionTextColor(type)

    private fun resolveLinkActionBackgroundColor(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.linkActionBackgroundColor(type)

    private fun resolveLinkActionTextColor(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.linkActionTextColor(type)

    private fun resolveBodyLinkIsUnderline(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.bodyLinkIsUnderLine(type)

    private fun resolveBodyLinkTextColor(
        hierarchy: AndesMessageHierarchyInterface,
        type: AndesMessageTypeInterface
    ) = hierarchy.bodyLinkTextColor(type)
}
