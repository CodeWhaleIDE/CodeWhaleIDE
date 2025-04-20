package com.bluewhaleyt.codewhaleide.common.sdk.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.common.extension.ui.maxLinesHeight
import com.bluewhaleyt.codewhaleide.common.extension.ui.onPrimaryAlt
import com.bluewhaleyt.codewhaleide.common.extension.ui.primaryAlt
import com.bluewhaleyt.codewhaleide.common.ui.highlighter
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelItem

@Composable
fun <T : ListPanelItem> ListPanel(
    panel: ListPanel<T>,
    modifier: Modifier = Modifier
) {
    with(panel) {
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

        Panel(
            modifier = modifier,
            panel = panel,
            onDismissRequest = { listener.onDismiss(panel) }
        ) {
            if (options.showInput) {
                InputPanelInput(
                    value = value,
                    onValueChange = {
                        value = it
                        listener.onValueChange(panel, it)
                    },
                    onDone = { listener.onConfirm(panel, value, selectedIndex, items[selectedIndex]) },
                    placeholder = options.placeholder?.let { { Text(it) } }
                )
            }
            LazyColumn {
                itemsIndexed(
                    items = items,
                    key = { i, _ -> i }
                ) { index, item ->
                    ListPanelItem(
                        selected = index == selectedIndex,
                        onSelected = {
                            selectedIndex = index
                            listener.onItemSelected(panel, value, index, item)
                        },
                        onReselected = { listener.onItemSelected(panel, value, index, item) },
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
                    )
                }
            }
        }
    }
}

@Composable
private fun ListPanelItem(
    selected: Boolean,
    onSelected: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onReselected: () -> Unit,
    description: @Composable (() -> Unit)? = null,
    detail: @Composable (() -> Unit)? = null
) {
    val containerColorSelected =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryAlt.copy(0.38f)
        else MaterialTheme.colorScheme.primaryAlt

    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryAlt
    else MaterialTheme.colorScheme.onSurface.copy(0.6f)

    val contentColorAlt = contentColor.copy(0.38f)

    ListPanelItem(
        modifier = Modifier
            .then(
                if (selected) Modifier.background(containerColorSelected)
                else Modifier
            )
            .then(modifier),
        label = {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                label()
            }
        },
        description = description?.let { {
            CompositionLocalProvider(LocalContentColor provides contentColorAlt) {
                it()
            }
        } },
        detail = detail?.let { {
            CompositionLocalProvider(LocalContentColor provides contentColorAlt) {
                it()
            }
        } },
        onClick = {
            if (selected) onReselected() else onSelected()
        }
    )
}

@Composable
private fun ListPanelItem(
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: @Composable (() -> Unit)? = null,
    detail: @Composable (() -> Unit)? = null
) {
    val contentAltStyle = MaterialTheme.typography.bodySmall

    ListPanelItemContainer(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ListPanelItemTextContainer(maxLines = 1) {
                        label()
                    }
                    description?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        CompositionLocalProvider(LocalTextStyle provides contentAltStyle,) {
                            ListPanelItemTextContainer(
                                modifier = Modifier.fillMaxWidth(),
                                style = contentAltStyle,
                                maxLines = 1
                            ) { it() }
                        }
                    }
                }
                detail?.let {
                    CompositionLocalProvider(LocalTextStyle provides contentAltStyle) {
                        ListPanelItemTextContainer(maxLines = 3, style = contentAltStyle) {
                            it()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListPanelItemContainer(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable { onClick() }
            .then(modifier)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
private fun ListPanelItemTextContainer(
    maxLines: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.maxLinesHeight(maxLines, style)) {
        content()
    }
}