package com.bluewhaleyt.codewhaleide.common.ui.quickpick

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.common.extension.ui.maxLinesHeight
import com.bluewhaleyt.codewhaleide.common.extension.ui.onPrimaryAlt
import com.bluewhaleyt.codewhaleide.common.extension.ui.primaryAlt
import com.bluewhaleyt.codewhaleide.sdk.ui.Icon

@Composable
internal fun QuickPickItem(
    selected: Boolean,
    onSelected: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onReselected: () -> Unit,
    description: @Composable (() -> Unit)? = null,
    detail: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val containerColorSelected =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryAlt.copy(0.38f)
        else MaterialTheme.colorScheme.primaryAlt

    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryAlt
    else MaterialTheme.colorScheme.onSurface.copy(0.6f)

    val contentColorAlt = contentColor.copy(0.38f)

    QuickPickItem(
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
        leadingIcon = leadingIcon,
        onClick = {
            if (selected) onReselected() else onSelected()
        }
    )
}

@Composable
private fun QuickPickItem(
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: @Composable (() -> Unit)? = null,
    detail: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val contentAltStyle = MaterialTheme.typography.bodySmall

    QuickPickItemContainer(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            leadingIcon?.let {
                Box(modifier = Modifier.size(24.dp)) {
                    it()
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    QuickPickItemTextContainer(maxLines = 1) {
                        label()
                    }
                    description?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        CompositionLocalProvider(LocalTextStyle provides contentAltStyle,) {
                            QuickPickItemTextContainer(
                                modifier = Modifier.fillMaxWidth(),
                                style = contentAltStyle,
                                maxLines = 1
                            ) { it() }
                        }
                    }
                }
                detail?.let {
                    CompositionLocalProvider(LocalTextStyle provides contentAltStyle) {
                        QuickPickItemTextContainer(maxLines = 3, style = contentAltStyle) {
                            it()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickPickItemContainer(
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
private fun QuickPickItemTextContainer(
    maxLines: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.maxLinesHeight(maxLines, style)) {
        content()
    }
}