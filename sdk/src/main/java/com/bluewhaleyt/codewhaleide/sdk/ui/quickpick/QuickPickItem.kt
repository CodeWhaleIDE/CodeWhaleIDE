package com.bluewhaleyt.codewhaleide.sdk.ui.quickpick

@JvmDefaultWithCompatibility
interface QuickPickItem {
    val label: String
    val description: String? get() = null
    val detail: String? get() = null
}