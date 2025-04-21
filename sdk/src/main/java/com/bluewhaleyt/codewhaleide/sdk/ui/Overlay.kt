package com.bluewhaleyt.codewhaleide.sdk.ui

import androidx.annotation.CallSuper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

abstract class Overlay internal constructor() : Component() {

    private val _visible = mutableStateOf(false)
    val visible by _visible

    @CallSuper
    fun show() {
        _visible.value = true
    }

    @CallSuper
    fun dismiss() {
        _visible.value = false
    }

}