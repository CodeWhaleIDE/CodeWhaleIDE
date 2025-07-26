package com.bluewhaleyt.codewhaleide.core.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import com.bluewhaleyt.codewhaleide.feature.settings.LocalSettingsManager
import com.bluewhaleyt.codewhaleide.feature.settings.Settings
import com.bluewhaleyt.codewhaleide.feature.settings.SettingsManager
import com.bluewhaleyt.codewhaleide.feature.settings.serializer.SettingsJsonSerializer

private const val FILE_NAME = "settings.json"

private val Context.dataStore by dataStore(
    fileName = FILE_NAME,
    serializer = SettingsJsonSerializer()
)

abstract class CoreActivity : ComponentActivity() {

    @Composable
    abstract fun App()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val settings by context.dataStore.data.collectAsState(initial = Settings())
            val settingsManager = SettingsManager(
                context = context,
                settings = settings,
                fileName = FILE_NAME,
                dataStore = context.dataStore
            )
            CompositionLocalProvider(LocalSettingsManager provides settingsManager) {
                App()
            }
        }
    }

}