package com.bluewhaleyt.codewhaleide.sdk.ui.panel.list

import com.bluewhaleyt.codewhaleide.sdk.ui.panel.Panel

class ListPanel <T : ListPanelItem> : Panel() {

    override lateinit var options: ListPanelOptions
    lateinit var listener: ListPanelListener<T>
    lateinit var items: List<T>

}