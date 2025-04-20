package com.bluewhaleyt.codewhaleide.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditorContainer(
    menu: @Composable () -> Unit,
    navigationBar: @Composable () -> Unit,
    statusBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .requiredWidth(56.dp)
                        .requiredHeight(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    menu()
                }
                Box(
                    modifier = Modifier
                        .requiredWidth(56.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    navigationBar()
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            statusBar()
        }
    }
}