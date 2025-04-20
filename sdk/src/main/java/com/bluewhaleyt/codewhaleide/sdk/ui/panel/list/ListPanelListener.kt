package com.bluewhaleyt.codewhaleide.sdk.ui.panel.list

import androidx.annotation.CallSuper

@JvmDefaultWithCompatibility
interface ListPanelListener <T : ListPanelItem> {

    @CallSuper
    fun onDismiss(panel: ListPanel<T>) = panel.dismiss()

    fun onConfirm(panel: ListPanel<T>, value: String, index: Int, item: T) =
        onItemSelected(panel, value, index, item)

    fun onValueChange(panel: ListPanel<T>, value: String) = Unit

    fun onItemSelected(panel: ListPanel<T>, value: String, index: Int, item: T)
}