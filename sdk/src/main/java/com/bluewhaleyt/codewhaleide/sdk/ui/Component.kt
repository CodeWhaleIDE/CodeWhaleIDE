package com.bluewhaleyt.codewhaleide.sdk.ui

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.compositionContext

fun <T : Component> component(
    component: T,
    view: View,
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val composeView = ComposeView(view.context).apply {
        setParentCompositionContext(compositionContext)
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
        setContent {
            if (component.visible) {
                Component(
                    component = component,
                    colorScheme = colorScheme,
                    modifier = modifier
                ) {
                    content()
                }
            }
        }
    }
    (view as ViewGroup).addView(composeView)
}

@Composable
private fun <T : Component> Component(
    component: T,
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val visible by remember { derivedStateOf { component.visible } }
    if (visible) {
        MaterialTheme(colorScheme) {
            Surface(modifier) {
                Box {
                    content()
                }
            }
        }
    }
}

abstract class Component {

    abstract val id: String

    private val _visible = mutableStateOf(true)
    val visible by _visible

    @CallSuper
    fun show() {
        _visible.value = true
    }

    @CallSuper
    fun dismiss() {
        _visible.value = false
    }

}