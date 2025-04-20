package com.bluewhaleyt.codewhaleide.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.app.sdk.ApplicationManifest
import com.bluewhaleyt.codewhaleide.app.sdk.LocalPluginContext
import com.bluewhaleyt.codewhaleide.sdk.Manifest

@Composable
fun EditorScreen() {
    val context = LocalPluginContext.current
    val manifest = Manifest.fromInputStream<ApplicationManifest>(
        context.assets.open("manifest.json")
    )

    Column(
        modifier = Modifier.fillMaxSize().safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello, CodeWhaleIDE!")

        LazyColumn {
            itemsIndexed(
                items = manifest.getAvailableActions(),
                key = { i, _ -> i }
            ) { index, action ->
                ListItem(
                    headlineContent = { Text(action.label) },
                    modifier = Modifier.clickable { action.asAction().onPerform(context) }
                )
            }
        }
    }
}