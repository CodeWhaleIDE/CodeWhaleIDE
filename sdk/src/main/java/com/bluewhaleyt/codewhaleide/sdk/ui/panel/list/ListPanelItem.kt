package com.bluewhaleyt.codewhaleide.sdk.ui.panel.list

@JvmDefaultWithCompatibility
interface ListPanelItem {
    val label: String
    val description: String? get() = null
    val detail: String? get() = null
}