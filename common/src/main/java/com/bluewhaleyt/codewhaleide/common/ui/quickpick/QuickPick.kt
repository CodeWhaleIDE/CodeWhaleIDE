package com.bluewhaleyt.codewhaleide.common.ui.quickpick

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.bluewhaleyt.codewhaleide.common.R
import com.bluewhaleyt.codewhaleide.common.extension.hideSystemBars
import com.bluewhaleyt.codewhaleide.common.extension.ui.clickableNoRipple
import com.bluewhaleyt.codewhaleide.common.ui.highlighter
import com.bluewhaleyt.codewhaleide.sdk.ui.Icon
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPick
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickItem

@Composable
fun <T : QuickPickItem> QuickPick(
    quickPick: QuickPick<T>,
    modifier: Modifier = Modifier
) {
    with(quickPick) {
        var value by remember { mutableStateOf(options.defaultValue ?: "") }
        var selectedIndex by remember { mutableStateOf(0) }

        val items = if (options.matchOnLabel || options.matchOnDescription || options.matchOnDetail) {
            items.filter { item ->
                val matchesLabel = options.matchOnLabel && item.label.contains(value, true)
                val matchesDescription = options.matchOnDescription && item.description?.contains(value, true) == true
                val matchesDetail = options.matchOnDetail && item.detail?.contains(value, true) == true
                matchesLabel || matchesDescription || matchesDetail
            }
        } else items

        LaunchedEffect(value, items) {
            selectedIndex = 0
        }

        QuickPick(
            modifier = modifier,
            onDismissRequest = { listener.onDismiss(quickPick) },
            title = options.title?.let { { Text(it) } }
        ) {
            AnimatedVisibility(options.showInput) {
                QuickPickInput(
                    value = value,
                    onValueChange = {
                        value = it
                        listener.onValueChange(quickPick, it)
                    },
                    onDone = {
                        listener.onConfirm(quickPick, value, selectedIndex, items[selectedIndex])
                    },
                    placeholder = options.placeholder?.let { { Text(it) } }
                )
            }
            LazyColumn {
                itemsIndexed(
                    items = items,
                    key = { i, _ -> i }
                ) { index, item ->
                    QuickPickItem(
                        selected = index == selectedIndex,
                        onSelected = {
                            selectedIndex = index
                            listener.onItemSelected(quickPick, value, index, item)
                        },
                        onReselected = { listener.onItemReselected(quickPick, value, index, item) },
                        label = {
                            if (options.matchOnLabel) {
                                Text(
                                    text = highlighter(
                                        text = item.label,
                                        textToHighlight = value,
                                        ignoreCase = true
                                    )
                                )
                            } else { Text(item.label) }
                        },
                        description = item.description?.let { {
                            if (options.matchOnDescription) {
                                Text(
                                    text = highlighter(
                                        text = it,
                                        textToHighlight = value,
                                        ignoreCase = true
                                    )
                                )
                            } else { Text(it) }
                        }},
                        detail = item.detail?.let { {
                            if (options.matchOnDetail) {
                                Text(
                                    text = highlighter(
                                        text = it,
                                        textToHighlight = value,
                                        ignoreCase = true
                                    )
                                )
                            } else { Text(it) }
                        }},
                        leadingIcon = item.icon?.let { { Icon(it) } }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickPick(
    title: @Composable (() -> Unit)?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    QuickPick(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            title?.let {
                QuickPickTitle {
                    it()
                }
            }
            content()
        }
    }
}

@Composable
private fun QuickPick(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicQuickPick(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickableNoRipple { onDismissRequest() }
                .imePadding()
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp)
                )
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicQuickPick(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val properties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        securePolicy = SecureFlagPolicy.Inherit,
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    )

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        val view = LocalView.current
        (view.parent as DialogWindowProvider).apply {
            window.apply {
                setGravity(Gravity.TOP)
                setWindowAnimations(R.style.CodeWhaleIDE_Component_Quick_Pick_Animation)
                hideSystemBars(view)
            }
        }
        content()
    }
}