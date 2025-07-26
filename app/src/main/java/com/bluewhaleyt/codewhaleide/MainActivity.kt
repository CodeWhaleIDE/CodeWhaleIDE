package com.bluewhaleyt.codewhaleide

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.core.activity.CoreActivity
import com.bluewhaleyt.codewhaleide.feature.settings.LocalSettingsManager
import com.bluewhaleyt.codewhaleide.feature.settings.SettingKeys
import kotlinx.coroutines.launch

class MainActivity : CoreActivity() {

    @Composable
    override fun App() {
        MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.safeDrawingPadding()) {
                    val scope = rememberCoroutineScope()
                    val settingsManager = LocalSettingsManager.current
                    val lineNumberEnabled = settingsManager.settings.editor.lineNumberEnabled

                    Switch(
                        checked = lineNumberEnabled,
                        onCheckedChange = {
                            scope.launch {
                                settingsManager.update(SettingKeys.EDITOR_LINE_NUMBER, it)
                            }
                        }
                    )

                    Text(settingsManager.jsonString)
                    Text(settingsManager.defaultJsonString)

                }
            }
        }
    }

}