package com.bluewhaleyt.codewhaleide.common.extension.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

val ColorScheme.primaryAlt
    @Composable get() = if (isSystemInDarkTheme()) primaryContainer else primary

val ColorScheme.onPrimaryAlt
    @Composable get() = if (isSystemInDarkTheme()) onPrimaryContainer else onPrimary