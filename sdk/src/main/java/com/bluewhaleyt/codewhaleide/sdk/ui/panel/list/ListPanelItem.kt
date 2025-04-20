package com.bluewhaleyt.codewhaleide.sdk.ui.panel.list

@JvmDefaultWithCompatibility
interface ListPanelItem {
    val label: String
    val description: String? get() = null
    val detail: String? get() = null
    val extraData: Any? get() = null
}

data class SimpleListPanelItem @JvmOverloads constructor(
    override val label: String,
    override val description: String? = null,
    override val detail: String? = null,
    override val extraData: Any? = null
) : ListPanelItem