package com.bluewhaleyt.codewhaleide.sdk.ui.panel.input

import com.bluewhaleyt.codewhaleide.sdk.ui.panel.Panel

class InputPanel : Panel() {

    override lateinit var options: InputPanelOptions
    lateinit var listener: InputPanelListener

}