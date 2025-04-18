package com.bluewhaleyt.codewhaleide.sdk.ui.quickpick

class QuickPickManager <T : QuickPickItem> internal constructor() {

    private val _quickPicks = mutableMapOf<String, QuickPick<T>>()
    val quickPicks = _quickPicks.toMap()

    fun add(quickPick: QuickPick<T>): QuickPick<T> {
        _quickPicks[quickPick.id] = quickPick
        return quickPick
    }

    companion object {
        fun <T : QuickPickItem> getInstance() =
            lazy { QuickPickManager<T>() }.value
    }

}