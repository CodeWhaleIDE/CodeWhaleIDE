package com.bluewhaleyt.codewhaleide.sdk.ui.panel

import com.bluewhaleyt.codewhaleide.sdk.ui.Overlay

abstract class Panel internal constructor(): Overlay() {
    abstract val options: PanelOptions
}