package com.bluewhaleyt.codewhaleide.app.action

import com.bluewhaleyt.codewhaleide.sdk.Action
import com.bluewhaleyt.codewhaleide.sdk.Manifest
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.SimpleListPanelItem

class AllActionsAction : Action {

    override fun onPerform(context: PluginContext) {
        context.showListPanel(
            items = context.manifest
                .getAvailableActions()
                .filter { it.id != "action.all.actions" }
                .map { SimpleListPanelItem(
                    label = it.label,
                    detail = it.description,
                    extraData = it
                ) },
            options = ListPanelOptions(
                placeholder = "Search actions..."
            ),
            listener = object : ListPanelListener<SimpleListPanelItem> {
                override fun onItemSelected(
                    panel: ListPanel<SimpleListPanelItem>,
                    value: String,
                    index: Int,
                    item: SimpleListPanelItem
                ) {
                    val action = item.extraData as Manifest.Action
                    action.asAction().onPerform(context)
                    panel.dismiss()
                }
            }
        )
    }

}