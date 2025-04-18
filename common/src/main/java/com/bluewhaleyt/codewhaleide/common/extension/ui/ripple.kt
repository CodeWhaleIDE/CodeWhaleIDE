package com.bluewhaleyt.codewhaleide.common.extension.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

val noRippleConfiguration
    get() = RippleConfiguration(
        color = Color.Transparent,
        rippleAlpha = RippleAlpha(0f, 0f, 0f, 0f)
    )

fun Modifier.clickableNoRipple(
    onClick: () -> Unit
) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) { onClick() }
}