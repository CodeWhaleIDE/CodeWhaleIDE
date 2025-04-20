package com.bluewhaleyt.codewhaleide.app.action

import android.widget.Toast
import com.bluewhaleyt.codewhaleide.sdk.Action
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelItem
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.SimpleListPanelItem

class KotlinTestAction : Action {

    override fun onPerform(context: PluginContext) {
        val items = listOf("Apple", "Orange", "Banana")
        context.showListPanel(
            items = items.map { SimpleListPanelItem(it) },
            options = ListPanelOptions(
                placeholder = "Search fruits..."
            ),
            listener = object : ListPanelListener<SimpleListPanelItem> {
                override fun onItemSelected(
                    panel: ListPanel<SimpleListPanelItem>,
                    value: String,
                    index: Int,
                    item: SimpleListPanelItem
                ) {
                    Toast.makeText(context, "Selected ${item.label}", Toast.LENGTH_SHORT).show()
                    panel.dismiss()
                }
            }
        )
    }

}