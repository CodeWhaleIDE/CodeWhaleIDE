package com.bluewhaleyt.codewhaleide.sdk

import android.content.Context
import android.content.ContextWrapper
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickItem
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickListener
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickOptions

abstract class PluginContext(baseContext: Context) : ContextWrapper(baseContext) {

    open fun <T : QuickPickItem> showQuickPick(
        id: String,
        items: List<T>,
        options: QuickPickOptions,
        listener: QuickPickListener<T>
    ) = Unit

}