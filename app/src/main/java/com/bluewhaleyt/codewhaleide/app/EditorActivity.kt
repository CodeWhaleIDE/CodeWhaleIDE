package com.bluewhaleyt.codewhaleide.app

import android.graphics.Typeface
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
import com.bluewhaleyt.codewhaleide.app.sdk.ApplicationManifest
import com.bluewhaleyt.codewhaleide.app.sdk.DefaultEditor
import com.bluewhaleyt.codewhaleide.app.sdk.DefaultPluginContext
import com.bluewhaleyt.codewhaleide.app.sdk.DefaultWorkspace
import com.bluewhaleyt.codewhaleide.app.sdk.LocalCodeEditor
import com.bluewhaleyt.codewhaleide.app.sdk.LocalPluginContext
import com.bluewhaleyt.codewhaleide.common.extension.hideSystemBars
import com.bluewhaleyt.codewhaleide.sdk.Manifest
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula

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

            val manifest = Manifest.fromInputStream<ApplicationManifest>(
                assets.open("manifest.json")
            )

            val editor = CodeEditor(this).apply {
                this.colorScheme = SchemeDarcula()
                val typeface = Typeface.createFromAsset(assets, "font/Iosevka-Regular.ttc")
                typefaceText = typeface
                typefaceLineNumber = typeface
            }

            CompositionLocalProvider(
                LocalPluginContext provides DefaultPluginContext(
                    data = PluginContext.Data(
                        context = this,
                        view = view,
                        colorScheme = colorScheme,
                        manifest = manifest,
                        workspace = DefaultWorkspace(),
                        editor = DefaultEditor(editor)
                    )
                ),
                LocalCodeEditor provides editor
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