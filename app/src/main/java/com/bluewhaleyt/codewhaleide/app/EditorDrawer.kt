package com.bluewhaleyt.codewhaleide.app

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("SwitchIntDef")
@Composable
internal fun EditorDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val sheetWidth = (if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        configuration.screenWidthDp / 2f
    } else configuration.screenWidthDp / 1.2f).dp

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE, -> {
            PermanentNavigationDrawer(
                modifier = modifier,
                drawerContent = {
                    AnimatedVisibility(drawerState.isOpen) {
                        PermanentDrawerSheet(
                            modifier = Modifier.width(sheetWidth),
                            drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ) {
                            drawerContent()
                        }
                    }
                },
                content = content
            )
        }
        else -> {
            ModalNavigationDrawer(
                modifier = modifier,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerState = drawerState,
                        modifier = Modifier.width(sheetWidth)
                    ) {
                        drawerContent()
                    }
                },
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                content = content
            )
        }
    }
}

@Composable
internal fun EditorDrawerMenuButton(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val (icon, contentDescription) = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> when (drawerState.currentValue) {
            DrawerValue.Open -> Icons.AutoMirrored.Filled.MenuOpen to "Close Menu"
            DrawerValue.Closed -> Icons.Filled.Menu to "Open Menu"
        }
        else -> Icons.Filled.Menu to "Menu"
    }

    IconButton(
        onClick = {
            scope.launch {
                with(drawerState) {
                    if (isOpen) close() else open()
                }
            }
        }
    ) {
        Icon(icon, contentDescription)
    }
}