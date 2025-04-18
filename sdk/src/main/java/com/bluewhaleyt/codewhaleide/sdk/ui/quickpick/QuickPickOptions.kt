package com.bluewhaleyt.codewhaleide.sdk.ui.quickpick

data class QuickPickOptions @JvmOverloads constructor(
    val title: String?,
    val placeholder: String? = null,
    val defaultValue: String? = null,
    val showInput: Boolean = true,
    val matchOnLabel: Boolean = true,
    val matchOnDescription: Boolean = false,
    val matchOnDetail: Boolean = false
)