package com.bluewhaleyt.codewhaleide.common.ui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun highlighter(
    text: CharSequence,
    textToHighlight: String,
    textToHighlightStyle: TextStyle = LocalTextStyle.current.copy(
        color = MaterialTheme.colorScheme.primary,
        background = MaterialTheme.colorScheme.primaryContainer.copy(0.6f)
    ),
    ignoreCase: Boolean = false
) = buildAnnotatedString {
    if (textToHighlight.isNotEmpty() && text.contains(textToHighlight, ignoreCase)) {
        val startIndex = text.indexOf(textToHighlight, ignoreCase = ignoreCase)
        val endIndex = startIndex + textToHighlight.length

        append(text.substring(0, startIndex))

        withStyle(textToHighlightStyle.toSpanStyle()) {
            append(text.substring(startIndex, endIndex))
        }

        append(text.substring(endIndex))
    } else {
        append(text)
    }
}