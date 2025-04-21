package com.bluewhaleyt.codewhaleide.common.ui

import android.content.res.Configuration
import android.view.Gravity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
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
    content: @Composable ColumnScope.() -> Unit
) {
    Panel(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            title?.let {
                PanelTitle { it() }
            }
            content()
        }
    }
}

@Composable
private fun Panel(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val panelMaxHeight = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> configuration.screenHeightDp.dp / 1.2f
        else -> configuration.screenHeightDp.dp / 2f
    }

    BasicPanel(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = panelMaxHeight)
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
    content: @Composable () -> Unit
) {
    val newProperties = DialogProperties(
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
                hideSystemBars()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickableNoRipple { onDismissRequest() }
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

data class PanelProperties(
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
    val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit
)