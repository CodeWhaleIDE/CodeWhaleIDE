package com.bluewhaleyt.codewhaleide.sdk

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.compose.material3.ColorScheme
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelItem
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions

abstract class PluginContext(
    protected open val data: Data
) : ContextWrapper(data.context), PluginContextProvider {

    open fun createInputPanel(): InputPanel = error("The method is not implemented")

    open fun <T : ListPanelItem> createListPanel(): ListPanel<T> = error("The method is not implemented")

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

    data class Data(
        val context: Context,
        val view: View,
        val colorScheme: ColorScheme,
        val workspace: Workspace,
        val editor: Editor
    )

}