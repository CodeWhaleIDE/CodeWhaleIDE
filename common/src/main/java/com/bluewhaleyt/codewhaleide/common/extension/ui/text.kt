package com.bluewhaleyt.codewhaleide.common.extension.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.roundToInt

internal fun Modifier.maxLinesHeight(
    maxLines: Int,
    textStyle: TextStyle
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "maxLinesHeight"
        properties["maxLines"] = maxLines
        properties["textStyle"] = textStyle
    }
) {
    require(maxLines > 0) {
        "maxLines must be greater than 0"
    }
    if (maxLines == Int.MAX_VALUE) return@composed Modifier

    val density = LocalDensity.current
    val fontFamilyResolver = LocalFontFamilyResolver.current
    val layoutDirection = LocalLayoutDirection.current

    // Difference between the height of two lines paragraph and one line paragraph gives us
    // an approximation of height of one line
    val resolvedStyle = remember(textStyle, layoutDirection) {
        resolveDefaults(textStyle, layoutDirection)
    }
    val typeface by remember(fontFamilyResolver, resolvedStyle) {
        fontFamilyResolver.resolve(
            resolvedStyle.fontFamily,
            resolvedStyle.fontWeight ?: FontWeight.Normal,
            resolvedStyle.fontStyle ?: FontStyle.Normal,
            resolvedStyle.fontSynthesis ?: FontSynthesis.All
        )
    }

    val firstLineHeight = remember(
        density,
        fontFamilyResolver,
        textStyle,
        layoutDirection,
        typeface
    ) {
        computeSizeForDefaultText(
            style = resolvedStyle,
            density = density,
            fontFamilyResolver = fontFamilyResolver,
            text = EmptyTextReplacement,
            maxLines = 1
        ).height
    }

    val firstTwoLinesHeight = remember(
        density,
        fontFamilyResolver,
        textStyle,
        layoutDirection,
        typeface
    ) {
        val twoLines = EmptyTextReplacement + "\n" + EmptyTextReplacement
        computeSizeForDefaultText(
            style = resolvedStyle,
            density = density,
            fontFamilyResolver = fontFamilyResolver,
            text = twoLines,
            maxLines = 2
        ).height
    }
    val lineHeight = firstTwoLinesHeight - firstLineHeight
    val precomputedMaxLinesHeight = firstLineHeight + lineHeight * (maxLines - 1)

    Modifier.heightIn(
        max = with(density) { precomputedMaxLinesHeight.toDp() }
    )
}

internal fun Modifier.minLinesHeight(
    minLines: Int,
    textStyle: TextStyle
) = composed {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val resolvedStyle = remember(textStyle, layoutDirection) {
        resolveDefaults(textStyle, layoutDirection)
    }
    val fontFamilyResolver = LocalFontFamilyResolver.current

    val heightOfTextLines = remember(
        density,
        textStyle,
        layoutDirection
    ) {
        val lines = (EmptyTextReplacement + "\n").repeat(minLines - 1)

        computeSizeForDefaultText(
            style = resolvedStyle,
            density = density,
            fontFamilyResolver = fontFamilyResolver,
            text = lines,
            maxLines = minLines,
        ).height
    }

    val heightInDp: Dp = with(density) { heightOfTextLines.toDp() }
    val heightToSet = heightInDp + OutlinedTextBoxDecoration

    Modifier.height(heightToSet)
}

private fun computeSizeForDefaultText(
    style: TextStyle,
    density: Density,
    fontFamilyResolver: FontFamily.Resolver,
    text: String = EmptyTextReplacement,
    maxLines: Int = 1
): IntSize {
    val paragraph = Paragraph(
        text = text,
        style = style,
        spanStyles = listOf(),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        density = density,
        fontFamilyResolver = fontFamilyResolver,
        constraints = Constraints()
    )
    return IntSize(paragraph.minIntrinsicWidth.ceilToIntPx(), paragraph.height.ceilToIntPx())
}

private const val DefaultWidthCharCount = 10
private val EmptyTextReplacement = "H".repeat(DefaultWidthCharCount)

private val OutlinedTextBoxDecoration = 40.dp

private fun Float.ceilToIntPx(): Int = ceil(this).roundToInt()