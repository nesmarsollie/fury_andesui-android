package com.mercadolibre.android.andesui.list.size

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize

/**
 * Defines all size related properties that an [AndesListViewItem] needs to be drawn properly.
 * Those properties change depending on the size of the list.
 */
@Suppress("TooManyFunctions")
internal interface AndesListViewItemSizeInterface {
    fun paddingLeft(context: Context): Int
    fun paddingRight(context: Context): Int
    fun paddingTop(context: Context): Int
    fun paddingBottom(context: Context): Int
    fun titleFontSize(context: Context): Float
    fun subTitleFontSize(context: Context): Float
    fun height(context: Context): Float
    fun titleMaxLines(context: Context): Int
    fun spaceBetweenTitleAndSubtitle(context: Context): Int
    fun separatorThumbnailWidth(context: Context): Int
    fun avatarSize(context: Context): AndesThumbnailSize
    fun iconSize(context: Context): Int
    fun chevronSize(context: Context): Int
    fun showSubtitle(context: Context): Boolean
}

@Suppress("TooManyFunctions")
internal class AndesListViewItemSmallSize : AndesListViewItemSizeInterface {
    override fun paddingLeft(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_left_right_small).toInt()

    override fun paddingRight(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_left_right_small).toInt()

    override fun paddingTop(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_top_small).toInt()

    override fun paddingBottom(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_bottom_small).toInt()

    override fun subTitleFontSize(context: Context): Float = 0f

    override fun titleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_title_font_size_small)

    override fun height(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_size_small)

    override fun titleMaxLines(context: Context) = 1

    override fun spaceBetweenTitleAndSubtitle(context: Context) = 0

    override fun separatorThumbnailWidth(context: Context):
            Int = context.resources.getDimension(R.dimen.andes_list_item_separator_thumbnail_width_small).toInt()

    override fun avatarSize(context: Context): AndesThumbnailSize = AndesThumbnailSize.SIZE_32

    override fun iconSize(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_icon_size_small).toInt()

    override fun chevronSize(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_chevron_size_small).toInt()

    override fun showSubtitle(context: Context): Boolean = false
}

@Suppress("TooManyFunctions")
internal class AndesListViewItemMediumSize : AndesListViewItemSizeInterface {
    override fun paddingLeft(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_left_right_small).toInt()

    override fun paddingRight(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_left_right_small).toInt()

    override fun paddingTop(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_top_medium).toInt()

    override fun paddingBottom(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_bottom_medium).toInt()

    override fun subTitleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_sub_title_font_size_medium)

    override fun titleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_title_font_size_medium)

    override fun height(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_size_medium)

    override fun titleMaxLines(context: Context) = 3

    override fun spaceBetweenTitleAndSubtitle(context: Context): Int =
            context.resources.getDimension(R.dimen.andes_list_item_space_between_title_subtitle_medium).toInt()

    override fun separatorThumbnailWidth(context: Context):
            Int = context.resources.getDimension(R.dimen.andes_list_item_separator_thumbnail_width_medium).toInt()

    override fun avatarSize(context: Context): AndesThumbnailSize = AndesThumbnailSize.SIZE_40

    override fun iconSize(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_icon_size_medium).toInt()

    override fun chevronSize(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_chevron_size_medium).toInt()

    override fun showSubtitle(context: Context): Boolean = true
}

@Suppress("TooManyFunctions")
internal class AndesListViewItemLargeSize : AndesListViewItemSizeInterface {
    override fun paddingLeft(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_left_right_medium).toInt()

    override fun paddingRight(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_left_right_medium).toInt()

    override fun paddingTop(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_top_large).toInt()

    override fun paddingBottom(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_padding_bottom_large).toInt()

    override fun subTitleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_sub_title_font_size_large)

    override fun titleFontSize(context: Context): Float = context.resources.getDimension(R.dimen.andes_list_item_title_font_size_large)

    override fun height(context: Context):
            Float = context.resources.getDimension(R.dimen.andes_list_item_size_large)

    override fun titleMaxLines(context: Context) = 3

    override fun spaceBetweenTitleAndSubtitle(context: Context):
            Int = context.resources.getDimension(R.dimen.andes_list_item_space_between_title_subtitle_large).toInt()

    override fun separatorThumbnailWidth(context: Context):
            Int = context.resources.getDimension(R.dimen.andes_list_item_separator_thumbnail_width_large).toInt()

    override fun avatarSize(context: Context): AndesThumbnailSize = AndesThumbnailSize.SIZE_56

    override fun iconSize(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_item_icon_size_large).toInt()

    override fun chevronSize(context: Context): Int = context.resources.getDimension(R.dimen.andes_list_chevron_size_large).toInt()

    override fun showSubtitle(context: Context): Boolean = true
}
