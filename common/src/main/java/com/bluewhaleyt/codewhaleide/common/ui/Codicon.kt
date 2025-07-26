package com.bluewhaleyt.codewhaleide.common.ui

import android.content.Context
import android.graphics.Typeface
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.common.extension.json5

@Composable
fun Codicon(
    name: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
    size: Dp = Dp.Unspecified
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val codicon = remember(context) { Codicon.create(context) }
    val darkTheme = isSystemInDarkTheme()

    codicon[name]?.let {
        Text(
            modifier = modifier
                .semantics {
                    this.contentDescription = contentDescription ?: name
                },
            text = it,
            fontFamily = FontFamily(codicon.typeface),
            fontSize = with(density) {
                (if (size == Dp.Unspecified) { 24.dp } else size).toSp()
            },
            color = when {
                tint != Color.Unspecified -> tint
                codicon.getColor(name, darkTheme) != null -> codicon.getColor(name, darkTheme)!!
                else -> LocalContentColor.current
            }
        )
    }
}

class Codicon private constructor(
    private val context: Context,
    private val codiconMap: Map<String, String>,
) {
    internal val typeface
        get() = Typeface.createFromAsset(context.assets, "codicon.ttf")

    internal fun getColor(name: String, darkTheme: Boolean): Color? {
        val symbolColor = when (name) {
            "symbol-enum",
            "symbol-event" -> Orange
            "symbol-enum-member",
            "symbol-field",
            "symbol-interface",
            "symbol-variable" -> Blue
            "symbol-function" -> Purple
            else -> null
        }
        return symbolColor?.let { if (darkTheme) it.second else it.first }
    }

    operator fun get(name: String) = codiconMap[name]

    companion object {
        fun create(context: Context) =
            Codicon(context, mapIcons(context))

        private fun mapIcons(context: Context): Map<String, String> {
            val jsonString = context.assets.open("codicons.json")
                .bufferedReader().use { it.readText() }
            val mapping: Map<String, Int> = json5.decodeFromString(jsonString)
            return mapping.mapValues { (_, codepoint) ->
                String(charArrayOf(codepoint.toChar()))
            }
        }
    }
}

private val Orange = Color(0xFFCC6633) to Color(0xFFEE9D28)
private val Blue = Color(0xFF007ACC) to Color(0xFF75BEFF)
private val Purple = Color(0xFF68217A) to Color(0xFFB180D7)