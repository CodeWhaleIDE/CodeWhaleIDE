package com.bluewhaleyt.codewhaleide.common.sdk.ui

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.compositionContext
import com.bluewhaleyt.codewhaleide.common.ui.Panel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.Panel

@Composable
internal fun <T : Panel> Panel(
    panel: T,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    with(panel) {
        Panel(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            title = options.title?.let { { Text(it) } }
        ) {
            content()
        }
    }
}

fun <T : Panel> createPanel(
    panel: T,
    view: View,
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val composeView = ComposeView(view.context).apply {
        setParentCompositionContext(compositionContext)
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
        setContent {
            val visible by remember { derivedStateOf { panel.visible } }
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
    }
    (view as ViewGroup).addView(composeView)
}