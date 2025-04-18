package com.bluewhaleyt.codewhaleide.app.action

import android.widget.Toast
import com.bluewhaleyt.codewhaleide.sdk.Action
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPick
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickItem
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickListener
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickOptions

class QuickPickAction : Action {

    override fun onPerform(context: PluginContext) {
        context.showQuickPick(
            id = "quickPick.test",
            items = mapOf(
                "HTML" to "Web Development",
                "JavaScript" to "Web, Frontend and Backend",
                "Kotlin" to "Multiplatform",
                "Java" to "Program"
            ).map { CustomItem(label = it.key, detail = it.value) },
            options = QuickPickOptions(
                title = "Fruits",
                placeholder = "Enter value"
            ),
            listener = object : QuickPickListener<CustomItem> {
                override fun onItemSelected(
                    quickPick: QuickPick<CustomItem>,
                    value: String,
                    index: Int,
                    item: CustomItem
                ) {
                    Toast.makeText(context, "Selected: ${item.label}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private data class CustomItem(
        override val label: String,
        override val detail: String?
    ) : QuickPickItem

}