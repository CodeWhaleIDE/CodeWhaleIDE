package com.bluewhaleyt.codewhaleide.sdk.ui.panel.input

import com.bluewhaleyt.codewhaleide.sdk.ui.panel.PanelOptions

data class InputPanelOptions @JvmOverloads constructor(
    override var title: String? = null,
    override var prompt: String? = null,
    override var dismissOnBackPress: Boolean = true,
    override var dismissOnClickOutside: Boolean = true,
    var placeholder: String? = null,
    var defaultValue: String? = null
) : PanelOptions