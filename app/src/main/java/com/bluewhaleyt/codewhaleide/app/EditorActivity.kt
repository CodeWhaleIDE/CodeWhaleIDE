package com.bluewhaleyt.codewhaleide.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import com.bluewhaleyt.codewhaleide.app.sdk.DefaultPluginContext
import com.bluewhaleyt.codewhaleide.common.extension.hideSystemBars
import com.bluewhaleyt.codewhaleide.sdk.LocalPluginContext

class EditorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val view = LocalView.current

            val darkTheme = isSystemInDarkTheme()
            val colorScheme = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (darkTheme) dynamicDarkColorScheme(this)
                    else dynamicLightColorScheme(this)
                }
                else -> {
                    if (darkTheme) darkColorScheme()
                    else lightColorScheme()
                }
            }

            CompositionLocalProvider(
                LocalPluginContext provides DefaultPluginContext(
                    context = this,
                    view = view,
                    colorScheme = colorScheme
                )
            ) {
                MaterialTheme(colorScheme) {
                    if (!view.isInEditMode) {
                        SideEffect {
                            window.hideSystemBars()
                        }
                    }
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.imePadding()) {
                            EditorScreen()
                        }
                    }
                }
            }

        }
    }

}