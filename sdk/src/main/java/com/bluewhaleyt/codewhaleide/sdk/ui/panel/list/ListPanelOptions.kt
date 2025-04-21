package com.bluewhaleyt.codewhaleide.sdk.ui.panel.list

import com.bluewhaleyt.codewhaleide.sdk.ui.panel.PanelOptions

data class ListPanelOptions @JvmOverloads constructor(
    override var title: String? = null,
    var placeholder: String? = null,
    var defaultValue: String? = null,
    var showInput: Boolean = true,
    var matchOnLabel: Boolean = true,
    var matchOnDescription: Boolean = false,
    var matchOnDetail: Boolean = false
) : PanelOptions