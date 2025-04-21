package com.bluewhaleyt.codewhaleide.app.sdk

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.bluewhaleyt.codewhaleide.common.noLocalProvidedFor
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import io.github.rosemoe.sora.widget.CodeEditor

val LocalPluginContext = staticCompositionLocalOf<PluginContext> {
    noLocalProvidedFor("LocalPluginContext")
}

val LocalCodeEditor = compositionLocalOf<CodeEditor> {
    noLocalProvidedFor("LocalCodeEditor")
}