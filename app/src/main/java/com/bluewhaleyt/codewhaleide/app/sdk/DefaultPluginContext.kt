package com.bluewhaleyt.codewhaleide.app.sdk

import android.content.Context
import android.view.View
import androidx.compose.material3.ColorScheme
import com.bluewhaleyt.codewhaleide.common.ui.quickpick.QuickPick
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import com.bluewhaleyt.codewhaleide.sdk.ui.component
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPick
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickItem
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickListener
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickManager
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickOptions

class DefaultPluginContext(
    private val context: Context,
    private val view: View,
    private val colorScheme: ColorScheme
) : PluginContext(context) {

    override fun <T : QuickPickItem> showQuickPick(
        id: String,
        items: List<T>,
        options: QuickPickOptions,
        listener: QuickPickListener<T>
    ) {
        val quickPick = QuickPickManager.getInstance<T>()
            .add(QuickPick(id))
            .apply {
                this.options = options
                this.items = items
                this.listener = listener
            }
        component(quickPick, view, colorScheme) {
            QuickPick(quickPick)
        }
    }

}