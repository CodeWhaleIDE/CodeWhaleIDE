package com.bluewhaleyt.codewhaleide.common.ui

import android.view.Gravity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.bluewhaleyt.codewhaleide.common.R
import com.bluewhaleyt.codewhaleide.common.extension.hideSystemBars
import com.bluewhaleyt.codewhaleide.common.extension.ui.clickableNoRipple
import com.bluewhaleyt.codewhaleide.common.extension.ui.minLinesHeight

@Composable
internal fun Panel(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    prompt: @Composable (() -> Unit)? = null,
    properties: PanelProperties = PanelProperties(),
    content: @Composable ColumnScope.() -> Unit
) {
    Panel(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            title?.let {
                PanelTitle { it() }
            }
            content()
            prompt?.let {
                PanelPrompt { it() }
            }
        }
    }
}

@Composable
private fun Panel(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: PanelProperties = PanelProperties(),
    content: @Composable () -> Unit
) {
    BasicPanel(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            shape = MaterialTheme.shapes.small.copy(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp)
            ),
            elevation = CardDefaults.elevatedCardElevation(0.dp)
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicPanel(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: PanelProperties = PanelProperties(),
    content: @Composable () -> Unit
) {
    val newProperties = DialogProperties(
        dismissOnBackPress = properties.dismissOnBackPress,
        dismissOnClickOutside = properties.dismissOnClickOutside,
        securePolicy = properties.securePolicy,
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    )

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        properties = newProperties
    ) {
        val view = LocalView.current
        (view.parent as DialogWindowProvider).apply {
            window.apply {
                setGravity(Gravity.TOP)
                setWindowAnimations(R.style.CodeWhaleIDE_Panel_Animation)
                if (!properties.dismissOnBackPress || !properties.dismissOnClickOutside) {
                    setDimAmount(0f)
                }
                hideSystemBars()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickableNoRipple {
                    if (properties.dismissOnClickOutside) {
                        onDismissRequest()
                    }
                }
                .imePadding()
        ) {
            content()
        }
    }
}

@Composable
private fun PanelTitle(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(24.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .minLinesHeight(
                    minLines = 1,
                    textStyle = LocalTextStyle.current
                )
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(0.6f)) {
                text()
            }
        }
    }
}

@Composable
private fun PanelPrompt(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(0.6f)) {
            text()
        }
    }
}

data class PanelProperties(
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
    val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit
)