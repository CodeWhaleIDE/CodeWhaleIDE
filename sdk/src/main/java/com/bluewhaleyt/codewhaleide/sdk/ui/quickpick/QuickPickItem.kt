package com.bluewhaleyt.codewhaleide.sdk.ui.quickpick

import com.bluewhaleyt.codewhaleide.sdk.ui.Icon

@JvmDefaultWithCompatibility
interface QuickPickItem {
    val label: String
    val description: String? get() = null
    val detail: String? get() = null
    val icon: Icon? get() = null
}