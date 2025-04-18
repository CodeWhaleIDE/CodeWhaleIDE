package com.bluewhaleyt.codewhaleide.sdk

interface Action {
    fun onPerform(context: PluginContext)
}