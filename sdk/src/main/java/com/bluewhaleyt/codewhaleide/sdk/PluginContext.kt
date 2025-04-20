package com.bluewhaleyt.codewhaleide.sdk

import android.content.Context
import android.content.ContextWrapper
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelItem
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions

abstract class PluginContext(baseContext: Context) : ContextWrapper(baseContext) {

    open fun createInputPanel(): InputPanel = error("Not Implemented!")

    open fun <T : ListPanelItem> createListPanel(): ListPanel<T> = error("Not Implemented")

    @JvmOverloads
    open fun showInputPanel(
        options: InputPanelOptions = InputPanelOptions(),
        listener: InputPanelListener
    ) = Unit

    @JvmOverloads
    open fun <T : ListPanelItem> showListPanel(
        items: List<T>,
        options: ListPanelOptions = ListPanelOptions(),
        listener: ListPanelListener<T>
    ) = Unit

}