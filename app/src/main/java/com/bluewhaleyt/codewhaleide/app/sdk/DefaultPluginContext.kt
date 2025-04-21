package com.bluewhaleyt.codewhaleide.app.sdk

import com.bluewhaleyt.codewhaleide.common.sdk.ui.InputPanel
import com.bluewhaleyt.codewhaleide.common.sdk.ui.ListPanel
import com.bluewhaleyt.codewhaleide.common.sdk.ui.createPanel
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelItem
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions

class DefaultPluginContext(
    override val data: Data
) : PluginContext(data) {

    override fun getWorkspace() = data.workspace

    override fun getEditor() = data.editor

    override fun createInputPanel(): InputPanel {
        val panel = InputPanel()
        createPanel(panel, data.view, data.colorScheme) {
            InputPanel(panel)
        }
        return panel
    }

    override fun <T : ListPanelItem> createListPanel(): ListPanel<T> {
        val panel = ListPanel<T>()
        createPanel(panel, data.view, data.colorScheme) {
            ListPanel(panel)
        }
        return panel
    }

    override fun showInputPanel(options: InputPanelOptions, listener: InputPanelListener) {
        createInputPanel().apply {
            this.options = options
            this.listener = listener
        }.show()
    }

    override fun <T : ListPanelItem> showListPanel(
        items: List<T>,
        options: ListPanelOptions,
        listener: ListPanelListener<T>
    ) {
        createListPanel<T>().apply {
            this.items = items
            this.options = options
            this.listener = listener
        }.show()
    }

}