package com.bluewhaleyt.codewhaleide.app.sdk

import androidx.compose.runtime.staticCompositionLocalOf
import com.bluewhaleyt.codewhaleide.common.noLocalProvidedFor
import com.bluewhaleyt.codewhaleide.sdk.PluginContext

val LocalPluginContext = staticCompositionLocalOf<PluginContext> {
    noLocalProvidedFor("LocalPluginContext")
}