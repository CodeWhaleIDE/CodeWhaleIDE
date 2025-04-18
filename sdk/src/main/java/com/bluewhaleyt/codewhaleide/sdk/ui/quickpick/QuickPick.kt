package com.bluewhaleyt.codewhaleide.sdk.ui.quickpick

import com.bluewhaleyt.codewhaleide.sdk.ui.Component

class QuickPick <T : QuickPickItem>(
    override val id: String,
) : Component() {

    lateinit var options: QuickPickOptions
    lateinit var items: List<T>
    lateinit var listener: QuickPickListener<T>

}