package com.bluewhaleyt.codewhaleide.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardCommandKey
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bluewhaleyt.codewhaleide.app.action.AllActionsAction
import com.bluewhaleyt.codewhaleide.app.sdk.LocalCodeEditor
import com.bluewhaleyt.codewhaleide.app.sdk.LocalPluginContext

@Composable
fun EditorScreen() {
    val context = LocalPluginContext.current
    val editor = LocalCodeEditor.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    EditorDrawer(
        drawerState = drawerState,
        drawerContent = {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                EditorDrawerMenuButton(drawerState)
                AndroidView(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    factory = { editor }
                )
                Row(
                    modifier = Modifier.height(24.dp).padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { AllActionsAction().onPerform(context) }) {
                        Icon(Icons.Default.KeyboardCommandKey, null)
                    }
                }
            }
        }
    }
}