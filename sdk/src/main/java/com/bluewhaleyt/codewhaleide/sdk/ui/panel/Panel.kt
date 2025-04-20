package com.bluewhaleyt.codewhaleide.sdk.ui.panel

import androidx.annotation.CallSuper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.bluewhaleyt.codewhaleide.sdk.ui.Component

abstract class Panel internal constructor(): Component() {

    private val _visible = mutableStateOf(false)
    val visible by _visible

    abstract val options: PanelOptions

    @CallSuper
    fun show() {
        _visible.value = true
    }

    @CallSuper
    fun dismiss() {
        _visible.value = false
    }

}