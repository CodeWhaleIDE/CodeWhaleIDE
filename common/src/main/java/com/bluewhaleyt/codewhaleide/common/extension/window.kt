package com.bluewhaleyt.codewhaleide.common.extension

import android.view.View
import android.view.Window
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@get:JvmSynthetic
val noWindowInsets
    get() = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)

fun Window.hideSystemBars(view: View = decorView) =
    getInsetsControllerCompat(view)
        .setVisible(WindowInsetsCompat.Type.systemBars(), false)

private fun Window.getInsetsControllerCompat(view: View) =
    WindowInsetsControllerCompat(this, view)

private fun WindowInsetsControllerCompat.setVisible(
    types: Int,
    visible: Boolean
) = run {
    if (visible) {
        show(types)
    } else {
        hide(types)
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}