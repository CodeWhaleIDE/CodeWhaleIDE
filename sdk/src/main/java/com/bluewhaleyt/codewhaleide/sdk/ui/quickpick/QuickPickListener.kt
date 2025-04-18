package com.bluewhaleyt.codewhaleide.sdk.ui.quickpick

import androidx.annotation.CallSuper

@JvmDefaultWithCompatibility
interface QuickPickListener <T : QuickPickItem> {

    @CallSuper
    fun onDismiss(quickPick: QuickPick<T>) = quickPick.dismiss()

    fun onConfirm(quickPick: QuickPick<T>, value: String, index: Int, item: T) =
        onItemSelected(quickPick, value, index, item)

    fun onValueChange(quickPick: QuickPick<T>, value: String) = Unit

    fun onItemSelected(quickPick: QuickPick<T>, value: String, index: Int, item: T)

    fun onItemReselected(quickPick: QuickPick<T>, value: String, index: Int, item: T) =
        onItemSelected(quickPick, value, index, item)
}