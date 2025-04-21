package com.bluewhaleyt.codewhaleide.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bluewhaleyt.codewhaleide.app.sdk.ApplicationManifest
import com.bluewhaleyt.codewhaleide.app.sdk.LocalCodeEditor
import com.bluewhaleyt.codewhaleide.app.sdk.LocalPluginContext
import com.bluewhaleyt.codewhaleide.sdk.Manifest
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanel
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelListener
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.ListPanelOptions
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.list.SimpleListPanelItem
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
fun EditorScreen() {
    val context = LocalPluginContext.current
    val manifest = Manifest.fromInputStream<ApplicationManifest>(
        context.assets.open("manifest.json")
    )
    val editor = LocalCodeEditor.current

    EditorContainer(
        menu = {
            IconButton(
                onClick = {
                    context.showListPanel(
                        items = manifest.getAvailableActions().map { SimpleListPanelItem(
                            label = it.label,
                            detail = it.description,
                            extraData = it
                        ) },
                        options = ListPanelOptions(
                            placeholder = "Search actions..."
                        ),
                        listener = object : ListPanelListener<SimpleListPanelItem> {
                            override fun onItemSelected(
                                panel: ListPanel<SimpleListPanelItem>,
                                value: String,
                                index: Int,
                                item: SimpleListPanelItem
                            ) {
                                val action = item.extraData as Manifest.Action
                                action.asAction().onPerform(context)
                                panel.dismiss()
                            }
                        }
                    )
                }
            ) {
                Icon(Icons.Default.Menu, "Menu")
            }
        },
        navigationBar = {},
        statusBar = {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Text("Hello, CodeWhaleIDE!")
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { editor }
            )
        }
    }
}