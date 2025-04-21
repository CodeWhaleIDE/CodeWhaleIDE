package com.bluewhaleyt.codewhaleide.app.action

import com.bluewhaleyt.codewhaleide.sdk.Action
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.SimpleListPanelItem
import io.github.rosemoe.sora.text.ContentIO
import java.io.File

class TestAction : Action {

    override fun onPerform(context: PluginContext) {
        val dir = context.workspace.project.directory
        val editor = context.editor

        if (dir.exists()) {
            dir.listFiles()?.let {
                context.showListPanel(
                    items = it.map { SimpleListPanelItem(
                        label = it.name,
                        description = it.parent,
                        detail = it.absolutePath,
                        extraData = it
                    ) },
                    listener = object : ListPanelListener<SimpleListPanelItem> {
                        override fun onItemSelected(
                            panel: ListPanel<SimpleListPanelItem>,
                            value: String,
                            index: Int,
                            item: SimpleListPanelItem
                        ) {
                            val file = item.extraData as File
                            editor.text = ContentIO.createFrom(file.inputStream())
                            panel.dismiss()
                        }
                    }
                )
            }
        }
    }

}