package com.bluewhaleyt.codewhaleide.common.ui.quickpick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.common.R
import com.bluewhaleyt.codewhaleide.common.extension.ui.primaryAlt

@Composable
internal fun QuickPickInput(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    placeholder: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    var focused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = MaterialTheme.shapes.small
            )
            .then(
                if (focused) Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primaryAlt,
                    shape = MaterialTheme.shapes.small
                ) else Modifier
            )
            .height(35.dp)
            .fillMaxWidth()
            .onFocusChanged { state ->
                focused = state.isFocused
            }
            .then(modifier),
        textStyle = LocalTextStyle.current.copy(
            color = LocalContentColor.current
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primaryAlt),
        maxLines = 1,
        minLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(0.38f)) {
                            placeholder?.invoke()
                        }
                    }
                    innerTextField()
                }
            }
        }
    )
}