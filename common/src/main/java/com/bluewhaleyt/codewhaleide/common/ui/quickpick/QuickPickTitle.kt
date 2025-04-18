package com.bluewhaleyt.codewhaleide.common.ui.quickpick

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.common.extension.ui.minLinesHeight
import kotlinx.serialization.json.JsonNull.content

@Composable
internal fun QuickPickTitle(
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