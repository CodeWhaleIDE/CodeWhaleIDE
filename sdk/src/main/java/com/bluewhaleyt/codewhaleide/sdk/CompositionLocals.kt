package com.bluewhaleyt.codewhaleide.sdk

import androidx.compose.runtime.staticCompositionLocalOf

fun noLocalProvidedFor(name: String): Nothing =
    error("CompositionLocal $name not present")

val LocalPluginContext = staticCompositionLocalOf<PluginContext> {
    noLocalProvidedFor("LocalPluginContext")
}