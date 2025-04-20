package com.bluewhaleyt.codewhaleide.sdk.ui.panel.input

import androidx.annotation.CallSuper

@JvmDefaultWithCompatibility
interface InputPanelListener {

    @CallSuper
    fun onDismiss(panel: InputPanel) = panel.dismiss()

    fun onConfirm(panel: InputPanel, value: String)

    fun onValueChange(panel: InputPanel, value: String) = Unit
}