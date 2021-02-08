package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import com.mercadolibre.android.andesui.color.AndesColor

/**
 * Receives a [BitmapDrawable] which will suffer some look overhauling that includes scaling and tinting based on received params such as size, color,
 * etc.
 * When the polishing ends, it will return a new [BitmapDrawable].
 * Size of the icon is based on Andes Specification.
 *
 * @param image image of the icon. Ok: The icon.
 * @param context needed for accessing some resources like size, you know.
 * @param dstWidth wanted width of the icon.
 * @param dstHeight wanted height of the icon.
 * @param colors we said we will be tinting the icon and this is the color. Note that the color for state_enabled will be used. If it does not exist,
 * 0 will be used.
 * @return a complete look overhauled [BitmapDrawable].
 */
fun buildColoredAndesBitmapDrawable(
    image: BitmapDrawable,
    context: Context,
    dstWidth: Int? = null,
    dstHeight: Int? = null,
    colors: ColorStateList?
): BitmapDrawable {
    val scaledBitmap: Bitmap = if (dstHeight != null && dstWidth != null) {
        Bitmap.createScaledBitmap(
            image.bitmap,
            dstWidth,
            dstHeight,
            true)
    } else {
        image.bitmap
    }

    return BitmapDrawable(context.resources, scaledBitmap)
            .apply {
                colors?.let {
                    if (isLollipopOrNewer()) {
                        setTintMode(PorterDuff.Mode.SRC_IN)
                        setTintList(it)
                    } else {
                        setColorFilter(it.getColorForState(intArrayOf(android.R.attr.state_enabled), 0), PorterDuff.Mode.SRC_IN)
                    }
                }
            }
}

fun buildColoredBitmapDrawable(
    image: BitmapDrawable,
    context: Context,
    dstWidth: Int? = null,
    dstHeight: Int? = null,
    color: Int?
): BitmapDrawable {
    val scaledBitmap: Bitmap = when {
        dstHeight != null && dstWidth != null -> Bitmap.createScaledBitmap(
                image.bitmap,
                dstWidth,
                dstHeight,
                true)
        else -> image.bitmap
    }
    return BitmapDrawable(context.resources, scaledBitmap)
            .apply {
                color?.let { setColorFilter(color, PorterDuff.Mode.SRC_IN) }
            }
}

fun buildColoredAndesBitmapDrawable(
    image: BitmapDrawable,
    context: Context,
    dstWidth: Int? = null,
    dstHeight: Int? = null,
    color: AndesColor? = null
): BitmapDrawable {
    val scaledBitmap: Bitmap = when {
        dstHeight != null && dstWidth != null -> Bitmap.createScaledBitmap(
                image.bitmap,
                dstWidth,
                dstHeight,
                true)
        else -> image.bitmap
    }
    return BitmapDrawable(context.resources, scaledBitmap)
            .apply {
                color?.let { setColorFilter(it.colorInt(context), PorterDuff.Mode.SRC_IN) }
            }
}

fun buildCircleBitmap(
    image: Bitmap,
    dstWidth: Int? = null,
    dstHeight: Int? = null
): Bitmap? {
    val scaledBitmap: Bitmap = when {
        dstHeight != null && dstWidth != null -> Bitmap.createScaledBitmap(
                image,
                dstWidth,
                dstHeight,
                true)
        else -> image
    }
    return getCircledBitmap(scaledBitmap)
}

fun getCircledBitmap(bitmap: Bitmap): Bitmap? {
    val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle((bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(), (bitmap.width / 2).toFloat(), paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}

/**
 * This method is the same as badge icon. When component were made, this should have been removed.
 */
fun buildColoredCircularShapeWithIconDrawable(
    image: BitmapDrawable,
    context: Context,
    iconColor: AndesColor? = null,
    shapeColor: Int? = null,
    diameter: Int
): Drawable {
    val icon = buildColoredAndesBitmapDrawable(image, context, null, null, iconColor)

    val biggerCircle = ShapeDrawable(OvalShape())
    biggerCircle.intrinsicHeight = diameter
    biggerCircle.intrinsicWidth = diameter
    biggerCircle.paint.color = shapeColor!!
    biggerCircle.bounds = Rect(0, 0, diameter, diameter)

    return LayerDrawable(arrayOf(biggerCircle, icon))
}

private fun isLollipopOrNewer() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun Drawable.toBitmap(): Bitmap? {
    var bitmap: Bitmap? = null
    if (this is BitmapDrawable) {
        val bitmapDrawable = this
        if (bitmapDrawable.bitmap != null) {
            return bitmapDrawable.bitmap
        }
    }
    bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    }
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}
