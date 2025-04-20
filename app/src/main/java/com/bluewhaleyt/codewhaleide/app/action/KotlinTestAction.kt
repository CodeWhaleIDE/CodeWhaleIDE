package com.bluewhaleyt.codewhaleide.app.action

import android.widget.Toast
import com.bluewhaleyt.codewhaleide.sdk.Action
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelItem
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener

class KotlinTestAction : Action {

    override fun onPerform(context: PluginContext) {
        val items = listOf("Apple", "Orange", "Banana")
        context.showListPanel(
            items = items.map { CustomItem(it) },
            listener = object : ListPanelListener<CustomItem> {
                override fun onItemSelected(
                    panel: ListPanel<CustomItem>,
                    value: String,
                    index: Int,
                    item: CustomItem
                ) {
                    Toast.makeText(context, "Selected ${item.label}", Toast.LENGTH_SHORT).show()
                    panel.dismiss()
                }
            }
        )
    }

    private data class CustomItem(
        override val label: String
    ) : ListPanelItem

}