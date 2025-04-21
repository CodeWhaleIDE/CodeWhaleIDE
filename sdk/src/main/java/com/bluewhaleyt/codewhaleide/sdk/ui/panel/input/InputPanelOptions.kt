package com.bluewhaleyt.codewhaleide.sdk.ui.panel.input

import com.bluewhaleyt.codewhaleide.sdk.ui.panel.PanelOptions

data class InputPanelOptions @JvmOverloads constructor(
    override var title: String? = null,
    var placeholder: String? = null,
    var defaultValue: String? = null
) : PanelOptions